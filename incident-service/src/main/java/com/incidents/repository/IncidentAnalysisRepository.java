package com.incidents.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incidents.entity.IncidentAnalysis;

public interface IncidentAnalysisRepository
        extends JpaRepository<IncidentAnalysis, Long> {

    Optional<IncidentAnalysis> findByIncidentId(Long incidentId);
}
