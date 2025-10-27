
package com.smartgrid.model;

public class EnergyStorage {
    private double capacity;
    private double level;
    private double efficiency = 0.9;

    public EnergyStorage(double capacity) {
        this.capacity = capacity;
        this.level = 0.0;
    }

    public void charge(double mwh) {
        double effective = mwh * efficiency;
        level = Math.min(capacity, level + effective);
    }

    public double discharge(double mwh) {
        double available = Math.min(level, mwh);
        level -= available;
        return available;
    }

    public double getLevel() {
        return level;
    }

    public double getCapacity() {
        return capacity;
    }
}
