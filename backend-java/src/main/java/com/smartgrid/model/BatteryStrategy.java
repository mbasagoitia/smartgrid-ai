package com.smartgrid.model;

import java.util.List;

public interface BatteryStrategy {
    void manageStorage(List<BatteryHub> hubs, double currentOutput, double currentDemand, String countyGeoid);
}
