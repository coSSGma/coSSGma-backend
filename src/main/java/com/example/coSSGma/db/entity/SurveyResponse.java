package com.example.coSSGma.db.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Map;

@Entity
@Data
public class SurveyResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ElementCollection
    private Map<String, String> answers; // 질문 응답 (예: {"Q1": "a", "Q2": "b"})

    private String sbti; // 계산된 학습 성향
}
