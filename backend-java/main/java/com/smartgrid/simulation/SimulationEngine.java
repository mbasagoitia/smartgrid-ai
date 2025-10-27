
package com.smartgrid.simulation;

import com.smartgrid.model.Region;
import com.smartgrid.model.BatteryStrategy;
import com.smartgrid.model.ConservativeStorageStrategy;
import com.smartgrid.model.AggressiveStorageStrategy;
import com.smartgrid.model.PredictiveStorageStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimulationEngine {

    public SimulationResult run(Map<String,Object> payload, Map<String,Object> forecast) {
        List<Region> regions = new ArrayList<>();
        regions.add(new Region("RegionA", 1000));
        regions.add(new Region("RegionB", 800));

        // Choose battery strategy
        String strategyName = (String) payload.getOrDefault("battery_strategy", "Conservative");
        BatteryStrategy strategy;
        switch (strategyName) {
            case "Aggressive": strategy = new AggressiveStorageStrategy(); break;
            case "Predictive": strategy = new PredictiveStorageStrategy(); break;
            default: strategy = new ConservativeStorageStrategy();
        }

        // Run a year-long simulation
        double totalGenerated = 0.0;
        for (int month=0; month<12; month++) {
            double gen = 0.0;
            if (forecast != null && forecast.containsKey("county_forecasts")) {
                Object cf = forecast.get("county_forecasts");
                gen += monthlyGeneration;
            } else {
                gen += 300.0;
            }
            totalGenerated += gen;
            // Apply battery strategy to all regions
            for (Region r : regions) {
                strategy.manageStorage(r, gen/regions.size(), 250.0);
            }
        }
        SimulationResult res = new SimulationResult();
        res.setTotalGenerationMwh(totalGenerated);
        res.setMessage("Simulation ran with strategy: " + strategyName);
        return res;
    }
}
