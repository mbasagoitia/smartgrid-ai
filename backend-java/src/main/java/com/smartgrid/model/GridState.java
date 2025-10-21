package com.smartgrid.model;

import java.util.List;

public class GridState {
    private List<Double> generation;
    private List<Double> consumption;
    private List<Double> storage;

    public GridState() {
        this.generation = List.of(100.0, 120.0);
        this.consumption = List.of(90.0, 110.0);
        this.storage = List.of(300.0);
    }

    public void update() {
        // Simulate next time step
    }

    // getters & setters
}

// List <Region> regions
    // Region (name, lat lon)
    // List<GridNode> nodes
        // PowerGenerator (generatorType, capacity, output)
        // EnergyStorage (capacity, chargeLevel)
        // PowerConsumer (demand, cityName)

// List<TransmissionLine> (sourceNode, targetNode, distance, capacity, lossCoefficient)

// public static double haversine(double lat1, double lon1, double lat2, double lon2) {
//     final double R = 6371; // Earth radius km
//     double dLat = Math.toRadians(lat2 - lat1);
//     double dLon = Math.toRadians(lon2 - lon1);
//     double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
//                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
//                Math.sin(dLon/2) * Math.sin(dLon/2);
//     double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//     return R * c;
// }

// double powerTransferred = powerSent * Math.exp(-lossCoefficient * distance);