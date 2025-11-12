package com.smartgrid.model;

public class HydroGenerator extends Generator {

    private static final double STANDARD_CAPACITY_MW = 200.0;

    public HydroGenerator(double lat, double lon, String countyGeoid) {
        super("hydro", lat, lon, STANDARD_CAPACITY_MW, countyGeoid);
    }

    @Override
    public double getAnnualGenerationMWh(CountyProfile profile) {
        return getCapacityMW() * profile.getHydroFactor();
    }
}
