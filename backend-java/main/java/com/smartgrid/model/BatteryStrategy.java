
package com.smartgrid.model;

public interface BatteryStrategy {
    void manageStorage(Region region, double currentOutput, double currentDemand);
}
