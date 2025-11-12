package com.smartgrid.model;

import lombok.Data;

@Data
public class Placement {
    private String type;
    private double lat;
    private double lon;
    private int count;
    private String countyGeoid;

    public Placement() {}

    public Placement(String type, double lat, double lon, String countyGeoid) {
        this.type = type;
        this.lat = lat;
        this.lon = lon;
        this.countyGeoid = countyGeoid;
    }  

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCountyGeoid() {
        return countyGeoid;
    }

    public void setCountyGeoid(String countyGeoid) {
        this.countyGeoid = countyGeoid;
    }
}
