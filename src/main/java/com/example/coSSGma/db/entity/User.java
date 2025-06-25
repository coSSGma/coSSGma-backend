package com.example.coSSGma.db.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String schoolId;
    private String nickname;
    private String sbti;             // 학습 성향 (예: INDEPENDENT_PLANNER)
    private String gradeLevelScore;  // 성적 수준 (예: HIGH, MEDIUM, LOW)
}
