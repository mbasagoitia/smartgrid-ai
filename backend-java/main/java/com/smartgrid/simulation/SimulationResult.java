
package com.smartgrid.simulation;

public class SimulationResult {
    private double totalGenerationMwh;
    private String message;

    public double getTotalGenerationMwh() { return totalGenerationMwh; }
    public void setTotalGenerationMwh(double v) { this.totalGenerationMwh = v; }

    public String getMessage() { return message; }
    public void setMessage(String m) { this.message = m; }
}
