package com.smartgrid.controller;

import org.springframework.web.bind.annotation.*;
import com.smartgrid.model.GridState;
import com.smartgrid.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/simulation")
@CrossOrigin(origins = "*")
public class SimulationController {

    @Autowired
    private SimulationService simulationService;

    @GetMapping("/state")
    public GridState getCurrentState() {
        return simulationService.getGridState();
    }

    @PostMapping("/step")
    public GridState stepSimulation() {
        return simulationService.advanceSimulationStep();
    }

    @PostMapping("/reset")
    public void reset() {
        simulationService.resetSimulation();
    }
}
