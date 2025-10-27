
package com.smartgrid.service;

import com.smartgrid.simulation.SimulationEngine;
import com.smartgrid.simulation.SimulationResult;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SimulationService {

    private AIServiceAdapter aiAdapter = new AIServiceAdapter();

    public SimulationResult runSimulation(Map<String, Object> payload) {
        // Call AI service for forecast
        Map<String,Object> forecast = aiAdapter.requestForecast(payload);
        // Run simulation engine with forecast
        SimulationEngine engine = new SimulationEngine();
        SimulationResult result = engine.run(payload, forecast);
        return result;
    }
}
