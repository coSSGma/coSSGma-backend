package com.example.coSSGma.api.service;

import com.example.coSSGma.db.entity.SurveyResponse;
import com.example.coSSGma.db.entity.User;
import com.example.coSSGma.db.repository.SurveyResponseRepository;
import com.example.coSSGma.db.repository.UserRepository;
import com.example.coSSGma.api.dto.SurveyResponseDTO;
import com.example.coSSGma.api.dto.SurveyResultDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SurveyService {
    private final UserRepository userRepository;
    private final SurveyResponseRepository surveyResponseRepository;

    // SBTI 유형 정의
    private static final String[] SBTI_TYPES = {
            "INDEPENDENT_PLANNER",
            "COLLABORATIVE_LEADER",
            "FLEXIBLE_FREETHINKER",
            "GOAL_ORIENTED_ACHIEVER",
            "EMPATHETIC_COMMUNICATOR",
            "FLEXIBLE_NIGHT_OWL"
    };

    // 가중치 표 (Python 코드에서 가져옴)
    private static final Map<Integer, Map<String, double[]>> WEIGHTS = new HashMap<>();

    static {
        WEIGHTS.put(1, Map.of(
                "a", new double[]{1.0, 0.2, 0.1, 0.9, 0.3, 0.4},
                "b", new double[]{0.3, 0.6, 0.4, 0.4, 0.7, 0.5},
                "c", new double[]{0.1, 0.9, 0.8, 0.2, 0.8, 0.6},
                "d", new double[]{0.7, 0.5, 0.6, 0.6, 0.5, 0.9}
        ));
        WEIGHTS.put(2, Map.of(
                "a", new double[]{0.9, 0.2, 0.1, 0.8, 0.3, 0.4},
                "b", new double[]{0.4, 0.6, 0.3, 0.5, 0.7, 0.5},
                "c", new double[]{0.2, 0.8, 0.5, 0.3, 0.9, 0.6},
                "d", new double[]{0.3, 0.4, 0.9, 0.6, 0.5, 0.7}
        ));
        WEIGHTS.put(3, Map.of(
                "a", new double[]{0.2, 0.9, 0.3, 0.4, 0.5, 0.4},
                "b", new double[]{0.7, 0.5, 0.4, 0.6, 0.6, 0.5},
                "c", new double[]{0.3, 0.8, 0.6, 0.4, 0.9, 0.6},
                "d", new double[]{0.9, 0.2, 0.5, 0.7, 0.3, 0.7}
        ));
        WEIGHTS.put(4, Map.of(
                "a", new double[]{0.9, 0.3, 0.1, 0.8, 0.4, 0.3},
                "b", new double[]{0.4, 0.6, 0.5, 0.5, 0.7, 0.9},
                "c", new double[]{0.2, 0.5, 0.7, 0.4, 0.6, 0.5},
                "d", new double[]{0.3, 0.4, 0.9, 0.5, 0.5, 0.7}
        ));
        WEIGHTS.put(5, Map.of(
                "a", new double[]{0.6, 0.4, 0.2, 0.9, 0.3, 0.5},
                "b", new double[]{0.8, 0.5, 0.6, 0.7, 0.6, 0.8},
                "c", new double[]{0.3, 0.9, 0.5, 0.4, 0.8, 0.6},
                "d", new double[]{0.5, 0.6, 0.7, 0.6, 0.7, 0.9}
        ));
        WEIGHTS.put(6, Map.of(
                "a", new double[]{0.9, 0.3, 0.2, 0.8, 0.4, 0.5},
                "b", new double[]{0.5, 0.6, 0.4, 0.6, 0.7, 0.9},
                "c", new double[]{0.3, 0.8, 0.5, 0.4, 0.9, 0.6},
                "d", new double[]{0.4, 0.5, 0.9, 0.5, 0.6, 0.7}
        ));
        WEIGHTS.put(7, Map.of(
                "a", new double[]{0.2, 0.9, 0.3, 0.4, 0.6, 0.4},
                "b", new double[]{0.6, 0.7, 0.5, 0.7, 0.9, 0.6},
                "c", new double[]{0.7, 0.5, 0.6, 0.6, 0.7, 0.8},
                "d", new double[]{0.9, 0.2, 0.5, 0.8, 0.3, 0.7}
        ));
        WEIGHTS.put(8, Map.of(
                "a", new double[]{0.8, 0.4, 0.3, 0.7, 0.5, 0.2},
                "b", new double[]{0.6, 0.7, 0.5, 0.6, 0.8, 0.4},
                "c", new double[]{0.4, 0.6, 0.7, 0.5, 0.6, 0.9},
                "d", new double[]{0.3, 0.5, 0.6, 0.4, 0.5, 0.9}
        ));
        WEIGHTS.put(9, Map.of(
                "a", new double[]{0.9, 0.4, 0.2, 0.8, 0.3, 0.5},
                "b", new double[]{0.6, 0.5, 0.4, 0.7, 0.9, 0.6},
                "c", new double[]{0.4, 0.7, 0.9, 0.5, 0.6, 0.7},
                "d", new double[]{0.5, 0.6, 0.6, 0.6, 0.7, 0.8}
        ));
        WEIGHTS.put(10, Map.of(
                "a", new double[]{0.8, 0.4, 0.2, 0.9, 0.5, 0.3},
                "b", new double[]{0.7, 0.6, 0.5, 0.7, 0.7, 0.9},
                "c", new double[]{0.3, 0.9, 0.6, 0.4, 0.8, 0.5},
                "d", new double[]{0.4, 0.5, 0.9, 0.5, 0.6, 0.8}
        ));
    }

    public SurveyService(UserRepository userRepository, SurveyResponseRepository surveyResponseRepository) {
        this.userRepository = userRepository;
        this.surveyResponseRepository = surveyResponseRepository;
    }

    public SurveyResultDTO submitSurvey(SurveyResponseDTO dto) {
        // 사용자 조회
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 답변 검증
        Map<String, String> answers = dto.getAnswers();
        if (answers.size() != 10) {
            throw new RuntimeException("Exactly 10 answers are required");
        }
        List<String> answerList = answers.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        for (String answer : answerList) {
            if (!answer.matches("[a-d]")) {
                throw new RuntimeException("Invalid answer: " + answer + ". Answers must be 'a', 'b', 'c', or 'd'");
            }
        }

        // 답변 저장
        SurveyResponse surveyResponse = new SurveyResponse();
        surveyResponse.setUser(user);
        surveyResponse.setAnswers(answers);
        surveyResponseRepository.save(surveyResponse);

        // SBTI 예측
        String sbti = predictSbti(answerList);

        // 사용자 SBTI 업데이트
        user.setSbti(sbti);
        userRepository.save(user);

        // 결과 반환
        SurveyResultDTO result = new SurveyResultDTO();
        result.setUserId(user.getId());
        result.setSbti(sbti);
        return result;
    }

    private String predictSbti(List<String> answers) {
        double[] typeScores = new double[SBTI_TYPES.length];

        for (int q = 1; q <= 10; q++) {
            String answer = answers.get(q - 1);
            double[] scores = WEIGHTS.get(q).get(answer);
            for (int i = 0; i < SBTI_TYPES.length; i++) {
                typeScores[i] += scores[i];
            }
        }

        int maxIndex = 0;
        for (int i = 1; i < typeScores.length; i++) {
            if (typeScores[i] > typeScores[maxIndex]) {
                maxIndex = i;
            }
        }

        return SBTI_TYPES[maxIndex];
    }

    public SurveyResultDTO getSurveyResult(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new SurveyResultDTO(userId, user.getSbti());
    }
}
