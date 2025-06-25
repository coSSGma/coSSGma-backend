package com.example.coSSGma.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuizResponse {
    private String question;
    private List<String> options;
    private String answer;
}
