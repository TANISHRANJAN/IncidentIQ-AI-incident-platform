package com.incidents.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incidents.entity.Incident;

public interface IncidentRepository extends JpaRepository<Incident, Long> {
    
}
