
package com.smartgrid.model;

public class Region {
    private String name;
    private EnergyStorage storage;

    public Region(String name, double storageCapacity) {
        this.name = name;
        this.storage = new EnergyStorage(storageCapacity);
    }

    public String getName() { return name; }
    public EnergyStorage getStorage() { return storage; }
}
