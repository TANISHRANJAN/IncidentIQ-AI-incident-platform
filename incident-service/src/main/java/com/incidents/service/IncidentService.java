package com.incidents.service;

import com.incidents.dto.CreateIncidentRequest;
import com.incidents.dto.UpdateStatusRequest;
import com.incidents.entity.Incident;

import java.util.List;

public interface IncidentService {

    Incident createIncident(CreateIncidentRequest request);

    List<Incident> getAllIncidents();

    Incident getIncidentById(Long id);

    Incident updateIncidentStatus(Long id, UpdateStatusRequest request);
}