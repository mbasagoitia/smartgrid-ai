package com.smartgrid.model;

import jakarta.persistence.*;

@Entity
public class BatteryHub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Should also have county geojson or county identifier rather than lat/long, compute county centroid from that
    private double capacityMWh;
    private double storedMWh;

    // Need to adjust this to a realistic capacity... 100k was too low
    public static final double STANDARD_CAPACITY_MWH = 200_000;
    public static final double EFFICIENCY = 0.9;  

    public BatteryHub() {
        this.capacityMWh = STANDARD_CAPACITY_MWH;
        this.storedMWh = 0.0;
    }

    public Long getId() { return id; }
    public double getCapacityMWh() { return capacityMWh; }
    public void setCapacityMWh(double capacityMWh) { this.capacityMWh = capacityMWh; }
    public double getStoredMWh() { return storedMWh; }
    public void setStoredMWh(double storedMWh) { this.storedMWh = storedMWh; }

    public void charge(double amount) {
        storedMWh = Math.min(capacityMWh, storedMWh + amount * EFFICIENCY);
    }

    public void discharge(double amount) {
        storedMWh = Math.max(0.0, storedMWh - amount / EFFICIENCY);
    }
}
