package com.example.coSSGma.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuizSubmitFeedback {

    private String question;
    private boolean isCorrect;
    private String feedback;

}
