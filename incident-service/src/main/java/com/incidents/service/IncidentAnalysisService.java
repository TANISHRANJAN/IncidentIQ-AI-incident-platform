package com.incidents.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.incidents.dto.AIAnalysisRequest;
import com.incidents.dto.AIAnalysisResponse;
import com.incidents.entity.Incident;
import com.incidents.repository.IncidentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncidentAnalysisService {

    private final AIServiceClient aiServiceClient;

    private final IncidentRepository incidentRepository;

    public void analyze(
            Incident incident
    ) {

        System.out.println(
                "AI analysis started for incident "
                        + incident.getId()
        );

        AIAnalysisRequest request =
                new AIAnalysisRequest(
                        String.valueOf(
                                incident.getId()
                        ),
                        incident.getTitle(),
                        incident.getDescription(),
                        List.of(),
                        "incident-service"
                );

        AIAnalysisResponse response =
                aiServiceClient.analyze(
                        request
                );

        System.out.println(
                "AI response summary = "
                        + response.summary()
        );

        System.out.println(
                "AI response root cause = "
                        + response.rootCause()
        );

        incident.setAiSummary(
                response.summary()
        );

        incident.setRootCauseSuggestion(
                response.rootCause()
        );

        incidentRepository.save(
                incident
        );

        System.out.println(
                "Incident updated with AI fields"
        );
    }
}