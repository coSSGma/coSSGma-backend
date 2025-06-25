package com.example.coSSGma.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class QuizSubmitItem {

    private String question;

    @JsonProperty("selected")  // JSON 필드명 매핑
    private String selectedAnswer;

    @JsonProperty("answer")    // JSON 필드명 매핑
    private String correctAnswer;

}
