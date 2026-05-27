package com.incidents.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.incidents.dto.CreateIncidentRequest;
import com.incidents.dto.UpdateStatusRequest;
import com.incidents.entity.Incident;
import com.incidents.event.IncidentCreatedEvent;
import com.incidents.exception.ResourceNotFoundException;
import com.incidents.producer.IncidentEventProducer;
import com.incidents.repository.IncidentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncidentServiceImpl
        implements IncidentService {

    private final IncidentRepository incidentRepository;

    private final IncidentEventProducer incidentEventProducer;

    /*
     * AI analysis service
     */
    private final IncidentAnalysisService incidentAnalysisService;

    @Override
    public Incident createIncident(
            CreateIncidentRequest request
    ) {

        /*
         * build incident
         */
        Incident incident =
                Incident.builder()
                        .title(
                                request.getTitle()
                        )
                        .description(
                                request.getDescription()
                        )
                        .severity(
                                request.getSeverity()
                        )
                        .status(
                                "OPEN"
                        )
                        .createdAt(
                                LocalDateTime.now()
                        )
                        .build();

        /*
         * save in postgres
         */
        Incident savedIncident =
                incidentRepository.save(
                        incident
                );

        /*
         * publish kafka event
         */
        IncidentCreatedEvent event =
                IncidentCreatedEvent.builder()
                        .incidentId(
                                savedIncident.getId()
                        )
                        .title(
                                savedIncident.getTitle()
                        )
                        .status(
                                savedIncident.getStatus()
                        )
                        .createdAt(
                                savedIncident.getCreatedAt()
                        )
                        .build();

        incidentEventProducer
                .publishIncidentCreatedEvent(
                        event
                );

        /*
         * call FastAPI AI service
         */
        incidentAnalysisService.analyze(
                savedIncident
        );

        /*
         * return updated row
         */
        return incidentRepository
                .findById(
                        savedIncident.getId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Incident not found"
                        ));
    }

    @Override
    public List<Incident> getAllIncidents() {

        return incidentRepository.findAll();
    }

    @Override
    public Incident getIncidentById(
            Long id
    ) {

        return incidentRepository
                .findById(
                        id
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Incident not found"
                        ));
    }

    @Override
    public Incident updateIncidentStatus(
            Long id,
            UpdateStatusRequest request
    ) {

        Incident incident =
                getIncidentById(
                        id
                );

        incident.setStatus(
                request.getStatus()
        );

        return incidentRepository.save(
                incident
        );
    }
}