package com.smartgrid.service.external;

import com.smartgrid.model.RenewableMix;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RenewableDataFacade {

    private final PVWattsService pvService;
    private final WindService windService;
    private final HydroService hydroService;

    public RenewableDataFacade(PVWattsService pvService, WindService windService, HydroService hydroService) {
        this.pvService = pvService;
        this.windService = windService;
        this.hydroService = hydroService;
    }

    // Combines solar, wind, and hydro generation data (MWh/MW) by county FIPS.
    public Map<String, RenewableMix> getRenewableMixForState(List<Map<String, Object>> countyList, String stateAbbr) {
        Map<String, Double> solarMap = pvService.getGenerationPerCounty(countyList);
        Map<String, Double> hydroMap = hydroService.getGenerationPerCounty(countyList);
        Map<String, Double> windMap = windService.getGenerationPerCounty(countyList);

        Map<String, RenewableMix> result = new HashMap<>();

        for (Map<String, Object> county : countyList) {
            String fips = (String) county.get("fips");
            if (!fips.startsWith(getFipsPrefix(stateAbbr))) continue;

            RenewableMix mix = new RenewableMix(
                    solarMap.getOrDefault(fips, 0.0),
                    windMap.getOrDefault(fips, 0.0),
                    hydroMap.getOrDefault(fips, 0.0)
            );
            result.put(fips, mix);
        }

        return result;
    }

    private String getFipsPrefix(String stateAbbr) {
        Map<String, String> fipsPrefixes = Map.ofEntries(
                Map.entry("CO", "08"), Map.entry("CA", "06"), Map.entry("TX", "48"),
                Map.entry("NY", "36"), Map.entry("FL", "12"), Map.entry("WA", "53")
        );
        return fipsPrefixes.getOrDefault(stateAbbr, "");
    }
}
