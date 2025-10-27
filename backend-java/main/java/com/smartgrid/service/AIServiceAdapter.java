
package com.smartgrid.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Map;

@Service
public class AIServiceAdapter {
    private RestTemplate rest = new RestTemplate();

    @Value("${ai.service.url:http://ai-service:8000/forecast/predict}")
    private String aiUrl;

    public Map<String, Object> requestForecast(Map<String, Object> payload) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ObjectMapper mapper = new ObjectMapper();
            String body = mapper.writeValueAsString(payload);
            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            ResponseEntity<Map> resp = rest.postForEntity(aiUrl, entity, Map.class);
            return resp.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("error","ai request failed");
        }
    }
}
