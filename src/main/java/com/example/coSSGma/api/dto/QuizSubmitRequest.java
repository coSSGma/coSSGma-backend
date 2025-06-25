package com.example.coSSGma.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuizSubmitRequest {
    private List<QuizSubmitItem> submissions;

}
