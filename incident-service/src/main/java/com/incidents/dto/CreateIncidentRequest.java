package com.incidents.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateIncidentRequest {
    
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String severity;   
}
