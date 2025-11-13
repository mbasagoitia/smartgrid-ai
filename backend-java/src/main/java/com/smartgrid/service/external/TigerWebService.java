package com.smartgrid.service.external;

import org.springframework.stereotype.Service;
import java.net.http.*;
import java.net.URI;
import java.time.Duration;
import java.util.Map;

@Service
public class TigerWebService {

    private static final String TIGERWEB_URL =
        "https://tigerweb.geo.census.gov/arcgis/rest/services/TIGERweb/State_County/MapServer/1/query";

    private static final HttpClient httpClient = HttpClient.newHttpClient();

    // Add all states later and abstract into a helper function? Use multipe times...
    private static final Map<String, String> STATE_CODES = Map.of(
        "CA", "06",
        "CO", "08",
        "TX", "48"
    );

    public String getCounties(String state) {
        state = state.toUpperCase();
        if (!STATE_CODES.containsKey(state)) {
            throw new IllegalArgumentException("Unsupported state: " + state);
        }

        String stateCode = STATE_CODES.get(state);
        String url = String.format(
            "%s?where=STATE='%s'&outFields=NAME,GEOID,STATE,COUNTY&outSR=4326&returnGeometry=true&f=geojson",
            TIGERWEB_URL, stateCode
        );

        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(20))
                .GET()
                .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            
            if (!body.contains("\"features\"")) {
                throw new RuntimeException("TIGERweb response does not contain 'features'. Check URL or parameters.");
            }

            return body;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to retrieve county data for " + state, e);
        }
    }

}
