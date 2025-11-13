package com.smartgrid.service.external;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.*;
import java.time.Duration;
import java.util.Map;
import java.util.Random;

@Service
public class EIAService {

    private final HttpClient http = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final CensusService censusService;

    @Value("${eia.api.key}")
    private String apiKey;

    private static final Map<String, Double> DEFAULTS = Map.of(
            "CA", 6.0, "TX", 14.0, "CO", 9.0
    );

    private static final Random RANDOM = new Random();

    public EIAService(CensusService censusService) {
        this.censusService = censusService;
    }

    public double fetchStatePerCapitaDemand(String state) {
        try {
            if (apiKey == null || apiKey.isBlank()) {
                return DEFAULTS.getOrDefault(state, 9.0);
            }

            String url = String.format(
                    "https://api.eia.gov/v2/electricity/retail-sales/data/?api_key=%s&facets[stateid][]=%s&frequency=annual&data[]=sales&start=2022",
                    apiKey, state
            );

            String body = http.send(
                            HttpRequest.newBuilder()
                                    .uri(URI.create(url))
                                    .timeout(Duration.ofSeconds(20))
                                    .GET()
                                    .build(),
                            HttpResponse.BodyHandlers.ofString()
                    ).body();

            JsonNode data = mapper.readTree(body).path("response").path("data");
            if (!data.isArray() || data.size() == 0) return DEFAULTS.getOrDefault(state, 9.0);

            double totalSalesMwh = 0;
            int latestYear = 0;
            for (JsonNode node : data) {
                String sector = node.path("sectorid").asText();
                int year = node.path("period").asInt();
                if ("ALL".equals(sector) && year > latestYear) {
                    latestYear = year;
                    totalSalesMwh = node.path("sales").asDouble() * 1000; // million kWh → MWh
                }
            }

            long population = censusService.fetchStatePopulation(state);
            if (population <= 0) return DEFAULTS.getOrDefault(state, 9.0);

            double perCapitaDemand = totalSalesMwh / population;
            return perCapitaDemand;

        } catch (Exception e) {
            e.printStackTrace();
            return DEFAULTS.getOrDefault(state, 9.0);
        }
    }

    // Estimates county-level per-capita electricity demand (MWh/person/year) using state per-capita baseline, urban/rural adjustment, and small random variation.
    public double estimateCountyPerCapitaDemand(String state, long countyPopulation, boolean isUrban) {
        double statePerCapita = fetchStatePerCapitaDemand(state);

        // Urban/rural multiplier
        double urbanFactor = isUrban ? 1.15 : 0.90;

        // Small random variation ±10%
        double variation = 0.9 + 0.2 * RANDOM.nextDouble();

        double countyPerCapita = statePerCapita * urbanFactor * variation;
        return countyPerCapita;
    }
}
