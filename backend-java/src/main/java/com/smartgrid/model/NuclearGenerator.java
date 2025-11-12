package com.smartgrid.model;

public class NuclearGenerator extends Generator {

    private static final double STANDARD_CAPACITY_MW = 500.0;

    public NuclearGenerator(double lat, double lon, String countyGeoid) {
        super("nuclear", lat, lon, STANDARD_CAPACITY_MW, countyGeoid);
    }

    @Override
    public double getAnnualGenerationMWh(CountyProfile profile) {
        return getCapacityMW() * profile.getNuclearFactor();
    }
}
