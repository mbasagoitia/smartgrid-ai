package com.smartgrid.model;

public class WindGenerator extends Generator {

    private static final double STANDARD_CAPACITY_MW = 150.0;

    public WindGenerator(double lat, double lon, String countyGeoid) {
        super("wind", lat, lon, STANDARD_CAPACITY_MW, countyGeoid);
    }

    @Override
    public double getAnnualGenerationMWh(CountyProfile profile) {
        return getCapacityMW() * profile.getWindFactor();
    }
}
