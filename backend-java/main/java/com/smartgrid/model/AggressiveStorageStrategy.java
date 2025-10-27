
package com.smartgrid.model;

public class AggressiveStorageStrategy implements BatteryStrategy {
    @Override
    public void manageStorage(Region region, double currentOutput, double currentDemand) {
        // release quickly to avoid any shortfall, charge aggressively
        double surplus = currentOutput - currentDemand;
        if (surplus > 0) {
            region.getStorage().charge(surplus);
        } else {
            double need = -surplus;
            region.getStorage().discharge(need);
        }
    }
}
