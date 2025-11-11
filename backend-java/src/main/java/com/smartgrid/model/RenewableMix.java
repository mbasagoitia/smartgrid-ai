package com.smartgrid.model;

public class RenewableMix {
    private double solarMwh;
    private double windMwh;
    private double hydroMwh;

    // Standard plant sizes for the simulation (MW)
    public static final double SOLAR_PLANT_SIZE_MW = 100;
    public static final double WIND_PLANT_SIZE_MW = 150;
    public static final double HYDRO_PLANT_SIZE_MW = 200;

    // Capacity factors
    public static final double SOLAR_CF = 0.20;
    public static final double WIND_CF = 0.35;
    public static final double HYDRO_CF = 0.50;

    // Hours per year
    private static final double HOURS_PER_YEAR = 8760;

    // Default constructor
    public RenewableMix() {
        this.solarMwh = 0.0;
        this.windMwh = 0.0;
        this.hydroMwh = 0.0;
    }

    // Construct from number of plants per type (int) 
    public RenewableMix(int solarPlants, int windPlants, int hydroPlants) {
        this.solarMwh = solarPlants * SOLAR_PLANT_SIZE_MW * SOLAR_CF * HOURS_PER_YEAR;
        this.windMwh = windPlants * WIND_PLANT_SIZE_MW * WIND_CF * HOURS_PER_YEAR;
        this.hydroMwh = hydroPlants * HYDRO_PLANT_SIZE_MW * HYDRO_CF * HOURS_PER_YEAR;
    }

    // Construct directly from MWh values (double)
    public RenewableMix(double solarMwh, double windMwh, double hydroMwh) {
        this.solarMwh = solarMwh;
        this.windMwh = windMwh;
        this.hydroMwh = hydroMwh;
    }

    // Getters
    public double getSolarMwh() { return solarMwh; }
    public double getWindMwh() { return windMwh; }
    public double getHydroMwh() { return hydroMwh; }

    // Helper
    public double getTotalMwh() {
        return solarMwh + windMwh + hydroMwh;
    }

    @Override
    public String toString() {
        return String.format("RenewableMix{solar=%.2f, wind=%.2f, hydro=%.2f}", solarMwh, windMwh, hydroMwh);
    }
}
