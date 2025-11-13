package com.smartgrid.service.external;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.*;
import java.time.Duration;
import java.util.Map;

@Service
public class CensusService {

    private final HttpClient http = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${census.api.key}")
    private String apiKey;

    // Extend to all states later
    private static final Map<String, String> STATE_CODES = Map.of(
        "CA", "06",
        "CO", "08",
        "TX", "48"
    );

    public long fetchCountyPopulation(String state, String geoid) {
        try {
            if (geoid == null || geoid.length() < 5) return 0L;

            String stateFips = geoid.substring(0, 2);
            String countyFips = geoid.substring(2);

            String url = String.format(
                "https://api.census.gov/data/2020/dec/pl?get=P1_001N&for=county:%s&in=state:%s",
                countyFips, stateFips
            );
            if (apiKey != null && !apiKey.isBlank()) url += "&key=" + apiKey;

            HttpResponse<String> res = http.send(
                HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .timeout(Duration.ofSeconds(20))
                        .GET()
                        .build(),
                HttpResponse.BodyHandlers.ofString()
            );

            if (res.statusCode() != 200) return 0L;

            JsonNode arr = mapper.readTree(res.body());
            if (!arr.isArray() || arr.size() < 2 || arr.get(1).size() < 1) return 0L;

            return Math.max(arr.get(1).get(0).asLong(), 0L);

        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public long fetchStatePopulation(String state) {
        try {
            if (state == null || state.length() != 2) return 0L;

            String stateFips = STATE_CODES.get(state.toUpperCase());
            if (stateFips == null) return 0L;

            String url = String.format(
                "https://api.census.gov/data/2020/dec/pl?get=P1_001N,NAME&for=state:%s",
                stateFips
            );
            if (apiKey != null && !apiKey.isBlank()) url += "&key=" + apiKey;

            HttpResponse<String> res = http.send(
                HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .timeout(Duration.ofSeconds(20))
                        .GET()
                        .build(),
                HttpResponse.BodyHandlers.ofString()
            );

            if (res.statusCode() != 200) return 0L;

            JsonNode arr = mapper.readTree(res.body());
            if (!arr.isArray() || arr.size() < 2 || arr.get(1).size() < 1) return 0L;

            return Math.max(arr.get(1).get(0).asLong(), 0L);

        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    // Used to "weight" energy demands per county
    public boolean isCountyUrban(String state, String geoid) {
        long population = fetchCountyPopulation(state, geoid);
        return population > 100_000;
    }
}
