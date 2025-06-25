package com.example.coSSGma.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class QuizSubmitResponse {
    private List<QuizSubmitFeedback> results;   // 각 문항에 대한 채점 결과
    private String analysis;                    // 현재 성향 분석
    private List<String> methods;               // 추천 학습 방식
    private String scoreSummary;                // 점수 요약 ("총 N문제 중 M문제 정답")
    private String progressFeedback;            // 실력 향상도 요약 ("이전 대비 몇 점 성장")
}
