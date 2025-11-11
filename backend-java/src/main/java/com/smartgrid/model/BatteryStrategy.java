package com.smartgrid.model;

public interface BatteryStrategy {
    // Manages energy flow for a given simulation step; determined by user
    void manageStorage(EnergyStorage hub, double currentOutput, double currentDemand);
}
