package com.example.coSSGma.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MatchResultDTO {
    private String studyName;
    private String people;
    private String studyType;
    private String studyTime;
}
