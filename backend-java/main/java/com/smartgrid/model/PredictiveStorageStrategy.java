
package com.smartgrid.model;

public class PredictiveStorageStrategy implements BatteryStrategy {
    @Override
    public void manageStorage(Region region, double currentOutput, double currentDemand) {
        // placeholder: in real use, consult AI forecast to pre-charge for predicted shortages
        double surplus = currentOutput - currentDemand;
        if (surplus > 0) {
            region.getStorage().charge(surplus * 0.95);
        } else {
            double need = -surplus;
            region.getStorage().discharge(Math.min(need, region.getStorage().getLevel()));
        }
    }
}
