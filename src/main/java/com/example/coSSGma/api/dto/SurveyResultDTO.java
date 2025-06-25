package com.example.coSSGma.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SurveyResultDTO {
    private Long userId;
    private String sbti;
    private String trait;
    private String match;
}
