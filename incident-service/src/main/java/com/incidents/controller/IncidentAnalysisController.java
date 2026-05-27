package com.incidents.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incidents.entity.IncidentAnalysis;
import com.incidents.repository.IncidentAnalysisRepository;

@RestController
@RequestMapping("/analysis")
public class IncidentAnalysisController {

    private final IncidentAnalysisRepository repository;

    public IncidentAnalysisController(
            IncidentAnalysisRepository repository
    ) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public IncidentAnalysis get(
            @PathVariable Long id
    ) {

        return repository
                .findByIncidentId(id)
                .orElseThrow();
    }
}