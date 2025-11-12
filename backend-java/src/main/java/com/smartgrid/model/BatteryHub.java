package com.smartgrid.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.HashMap;
import java.util.Map;

@Entity
public class BatteryHub extends Placement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double storedMWh;
    public static final double EFFICIENCY = 0.9;
    // standardized battery hub size; adjusting to fit simulation
    public static final double STANDARD_CAPACITY_MWH = 10_000.0; 

    private transient Map<String, Double> lastDischargeMap = new HashMap<>();

    public BatteryHub(double lat, double lon, String countyGeoid) {
        super("battery", lat, lon, countyGeoid);
        this.storedMWh = 0.0;
    }

    public BatteryHub() {}

    public Long getId() { return id; }

    public double getCapacityMWh() { return STANDARD_CAPACITY_MWH; }
    public double getStoredMWh() { return storedMWh; }
    public void setStoredMWh(double storedMWh) { this.storedMWh = storedMWh; }

    public void charge(double amount) {
        storedMWh = Math.min(getCapacityMWh(), storedMWh + amount * EFFICIENCY);
    }

    public void discharge(double amount, String countyGeoid) {
        double actual = Math.min(storedMWh, amount / EFFICIENCY);
        storedMWh -= actual;
        lastDischargeMap.put(
            countyGeoid,
            lastDischargeMap.getOrDefault(countyGeoid, 0.0) + actual * EFFICIENCY
        );
    }

    public double getLastDischargeForCounty(String countyGeoid) {
        return lastDischargeMap.getOrDefault(countyGeoid, 0.0);
    }

    public void clearLastDischargeMap() {
        lastDischargeMap.clear();
    }
}
