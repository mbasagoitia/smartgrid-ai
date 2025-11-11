package com.smartgrid.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartgrid.model.CountyProfile;
import com.smartgrid.repository.CountyProfileRepository;
import com.smartgrid.service.external.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class CountyProfileService {

    private final CountyProfileRepository repo;
    private final TigerWebService tigerWebService;
    private final CensusService censusService;
    private final EIAService eiaService;
    private final WeatherDataFacade weatherDataFacade;
    private final ObjectMapper mapper = new ObjectMapper();

    public CountyProfileService(
            CountyProfileRepository repo,
            TigerWebService tigerWebService,
            CensusService censusService,
            EIAService eiaService,
            WeatherDataFacade weatherDataFacade) {
        this.repo = repo;
        this.tigerWebService = tigerWebService;
        this.censusService = censusService;
        this.eiaService = eiaService;
        this.weatherDataFacade = weatherDataFacade;
    }

    // Builds or retrieves all county profiles for a given state.
    public List<CountyProfile> getProfilesForState(String state) throws Exception {
        state = state.toUpperCase();

        String geojson = tigerWebService.getCounties(state);
        JsonNode features = mapper.readTree(geojson).path("features");
        if (!features.isArray()) {
            throw new RuntimeException("Invalid TIGERweb response for " + state);
        }

        List<CompletableFuture<CountyProfile>> futures = new ArrayList<>();

        for (JsonNode feature : features) {
            final JsonNode f = feature;
            final String s = state;

            futures.add(CompletableFuture.supplyAsync(() -> {
                try {
                    JsonNode props = f.path("properties");
                    String name = props.path("NAME").asText();
                    String geoid = props.path("GEOID").asText();

                    // Check cache first
                    Optional<CountyProfile> cachedOpt = repo.findByStateAndGeoid(s, geoid);
                    if (cachedOpt.isPresent()) {
                        CountyProfile cached = cachedOpt.get();
                        if (cached.getCachedAt() != null &&
                                cached.getCachedAt().isAfter(LocalDateTime.now().minusDays(7))) {
                            return cached;
                        }
                    }

                    CountyProfile profile = cachedOpt.orElseGet(() -> new CountyProfile(s, geoid, name));
                    profile.setGeojson(f.toString());

                    // Compute centroid
                    double[] centroid = computeCentroid(f.path("geometry"));
                    profile.setCentroidLat(centroid[1]);
                    profile.setCentroidLon(centroid[0]);

                    // Fetch data concurrently
                    CompletableFuture<Long> populationFuture =
                            CompletableFuture.supplyAsync(() -> censusService.fetchCountyPopulation(s, geoid));
                    CompletableFuture<Double> solarFuture = weatherDataFacade.fetchSolar(centroid[1], centroid[0]);
                    CompletableFuture<Double> windFuture = weatherDataFacade.fetchWind(geoid);
                    CompletableFuture<Double> hydroFuture = weatherDataFacade.fetchHydro(centroid[1], centroid[0]);

                    CompletableFuture.allOf(populationFuture, solarFuture, windFuture, hydroFuture).join();

                    long population = populationFuture.get();
                    double solarMwhPerMw = solarFuture.get();
                    double windFactor = windFuture.get();
                    double hydroFactor = hydroFuture.get();

                    boolean isUrban = censusService.isCountyUrban(s, geoid);
                    double perCapitaDemand = eiaService.estimateCountyPerCapitaDemand(s, population, isUrban);
                    double totalDemand = population * perCapitaDemand;

                    // Store results
                    profile.setPopulation(population);
                    profile.setDemandAnnualMwh(totalDemand);
                    profile.setSolarMwhPerMw(solarMwhPerMw);
                    profile.setWindFactor(windFactor);
                    profile.setHydroFactor(hydroFactor);

                    profile.setSolarCoverageRatio(Math.min(1.0, solarMwhPerMw / totalDemand));
                    profile.setWindCoverageRatio(Math.min(1.0, windFactor / totalDemand));
                    profile.setHydroCoverageRatio(Math.min(1.0, hydroFactor / totalDemand));

                    profile.setCachedAt(LocalDateTime.now());
                    return repo.save(profile);

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }));
        }

        return futures.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // Helpers
    private double[] computeCentroid(JsonNode geometry) {
        try {
            String type = geometry.path("type").asText();
            if ("Polygon".equals(type)) return averageCoords(geometry.path("coordinates").get(0));
            else if ("MultiPolygon".equals(type)) return averageCoords(geometry.path("coordinates").get(0).get(0));
        } catch (Exception ignored) {}
        // Eventually add a different fallback
        return new double[]{-105.0, 39.0};
    }

    private double[] averageCoords(JsonNode coords) {
        double sumX = 0, sumY = 0; int n = 0;
        for (JsonNode pt : coords) {
            sumX += pt.get(0).asDouble();
            sumY += pt.get(1).asDouble();
            n++;
        }
        return (n == 0) ? new double[]{-105, 39} : new double[]{sumX / n, sumY / n};
    }
}
