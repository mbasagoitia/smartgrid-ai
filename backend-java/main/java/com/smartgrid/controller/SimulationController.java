
package com.smartgrid.controller;

import com.smartgrid.service.SimulationService;
import com.smartgrid.simulation.SimulationResult;
import com.smartgrid.model.Region;
import com.smartgrid.model.PowerGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SimulationController {

    @Autowired
    private SimulationService service;

    @PostMapping("/simulate")
    public SimulationResult simulate(@RequestBody Map<String, Object> payload) {
        return service.runSimulation(payload);
    }

    @GetMapping("/strategies")
    public String[] strategies() {
        return new String[]{"Conservative","Aggressive","Predictive"};
    }
}
