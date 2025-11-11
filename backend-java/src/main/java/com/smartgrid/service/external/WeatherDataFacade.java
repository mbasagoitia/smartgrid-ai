package com.smartgrid.service.external;

import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
public class WeatherDataFacade {

    private final PVWattsService pvWattsService;
    private final WindService windService;
    private final HydroService hydroService;

    public WeatherDataFacade(PVWattsService pvWattsService, WindService windService, HydroService hydroService) {
        this.pvWattsService = pvWattsService;
        this.windService = windService;
        this.hydroService = hydroService;
    }

    public CompletableFuture<Double> fetchSolar(double lat, double lon) {
        return CompletableFuture.supplyAsync(() -> pvWattsService.fetchAnnualOutput(lat, lon));
    }

    public CompletableFuture<Double> fetchWind(String geoid) {
        return CompletableFuture.supplyAsync(() -> windService.getWindFactorByCounty(geoid));
    }

    public CompletableFuture<Double> fetchHydro(double lat, double lon) {
        return CompletableFuture.supplyAsync(() -> hydroService.fetchHydroPotential(lat, lon));
    }
}
