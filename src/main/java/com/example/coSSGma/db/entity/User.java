package com.example.coSSGma.db.entity;


import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Map;


@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String schoolId;
    private String nickname;
    private String sbti; // 학습 성향 (예: INDEPENDENT_PLANNER)
    private String gradeLevelScore; // 성적 수준 (예: HIGH, MEDIUM, LOW)
}
