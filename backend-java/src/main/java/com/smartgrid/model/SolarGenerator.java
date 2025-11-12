package com.smartgrid.model;

public class SolarGenerator extends Generator {

    private static final double STANDARD_CAPACITY_MW = 100.0;

    public SolarGenerator(double lat, double lon, String countyGeoid) {
        super("solar", lat, lon, STANDARD_CAPACITY_MW, countyGeoid);
    }

    @Override
    public double getAnnualGenerationMWh(CountyProfile profile) {
        return getCapacityMW() * profile.getSolarMwhPerMw();
    }
}
