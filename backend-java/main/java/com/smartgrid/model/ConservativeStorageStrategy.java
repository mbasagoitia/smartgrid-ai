
package com.smartgrid.model;

public class ConservativeStorageStrategy implements BatteryStrategy {
    @Override
    public void manageStorage(Region region, double currentOutput, double currentDemand) {
        // store unless battery near full, release only for shortages
        double surplus = currentOutput - currentDemand;
        if (surplus > 0) {
            region.getStorage().charge(surplus * 0.9);
        } else {
            double need = -surplus;
            region.getStorage().discharge(Math.min(need, region.getStorage().getLevel()));
        }
    }
}
