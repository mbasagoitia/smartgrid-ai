package com.smartgrid.service;

import com.smartgrid.model.*;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class SimulationService {

    private GridState currentState;
    private AIStrategy strategy;

    public SimulationService() {
        // Initialize grid nodes and strategy
        this.strategy = new GreedyStrategy();
        this.currentState = new GridState();
    }

    public GridState getGridState() {
        return currentState;
    }

    public GridState advanceSimulationStep() {
        // Apply strategy to modify grid behavior
        currentState.update();
        return currentState;
    }

    public void resetSimulation() {
        this.currentState = new GridState();
    }
}
