package com.smartgrid.service;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.smartgrid.model.GridState;
import reactor.core.publisher.Mono;

@Component
public class AIServiceAdapter {

    private final WebClient webClient;

    public AIServiceAdapter(WebClient.Builder builder) {
        this.webClient = builder.baseUrl(System.getenv("AI_SERVICE_URL")).build();
    }

    public Mono<String> getForecast(GridState state) {
        // Replace response type with model
        return webClient.post()
                .uri("/forecast")
                .bodyValue(state)
                .retrieve()
                .bodyToMono(String.class);
    }
}
