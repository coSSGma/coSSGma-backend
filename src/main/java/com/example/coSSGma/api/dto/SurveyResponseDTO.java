package com.example.coSSGma.api.dto;

import lombok.Data;

import java.util.Map;

@Data
public class SurveyResponseDTO {
    private Long userId;
    private Map<String, String> answers;
    private String trait;
    private String match;
}
