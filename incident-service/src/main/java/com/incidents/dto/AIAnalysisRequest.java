package com.incidents.dto;

import java.util.List;

public record AIAnalysisRequest(
        String incidentId,
        String title,
        String description,
        List<String> logs,
        String serviceName
) {}