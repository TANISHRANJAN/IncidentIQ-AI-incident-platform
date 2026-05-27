package com.incidents.service;



import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.incidents.dto.AIAnalysisRequest;
import com.incidents.dto.AIAnalysisResponse;

@Service
public class AIServiceClient {

    private final WebClient webClient;

    public AIServiceClient(
            WebClient webClient
    ) {
        this.webClient = webClient;
    }

    public AIAnalysisResponse analyze(
            AIAnalysisRequest request
    ) {

        return webClient.post()
                .uri("/analyze")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AIAnalysisResponse.class)
                .block();
    }
}