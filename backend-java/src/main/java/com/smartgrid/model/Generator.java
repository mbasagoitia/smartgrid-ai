package com.smartgrid.model;

public abstract class Generator extends Placement {
    protected double capacityMW;

    public Generator(String type, double lat, double lon, double capacityMW, String countyGeoid) {
        super(type, lat, lon, countyGeoid);
        this.capacityMW = capacityMW;
    }

    public double getCapacityMW() {
        return capacityMW;
    }

    public abstract double getAnnualGenerationMWh(CountyProfile profile);
}
