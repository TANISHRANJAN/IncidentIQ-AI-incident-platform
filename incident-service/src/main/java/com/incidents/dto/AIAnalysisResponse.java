package com.incidents.dto;

import java.util.List;

public record AIAnalysisResponse(
        String summary,
        String severity,
        String category,
        String rootCause,
        List<String> recommendations
) {}