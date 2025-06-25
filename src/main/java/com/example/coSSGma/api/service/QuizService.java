package com.example.coSSGma.api.service;

import com.example.coSSGma.api.dto.QuizResponse;
import com.example.coSSGma.api.dto.QuizSubmitFeedback;
import com.example.coSSGma.api.dto.QuizSubmitItem;
import com.example.coSSGma.api.dto.QuizSubmitRequest;
import com.example.coSSGma.api.dto.QuizSubmitResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${GPT_API_KEY}")
    private String GPT_API_KEY;
    private final String GPT_URL = "https://api.openai.com/v1/chat/completions";

    public List<QuizResponse> generateQuizFromContext(String summary) {
        String prompt = String.format("""
            다음 내용을 바탕으로 객관식 퀴즈 5문제를 JSON 배열로 출력해줘.
                    - JSON 코드 블록 없이 순수 JSON만 출력해줘.
                    - 각 퀴즈는 {"question": "...", "options": [...], "answer": "..."} 형식이야.
            각 문제는 "question", "options", "answer" 키를 포함해야 해.
            내용: %s
        """, summary);

        String gptResponse = callGpt(prompt);
        return parseQuizResponse(gptResponse);
    }

    private List<QuizResponse> parseQuizResponse(String gptResponse) {
        try {
            String content = objectMapper.readTree(gptResponse)
                    .path("choices").get(0).path("message").path("content").asText();
            JsonNode quizArray = objectMapper.readTree(content);
            List<QuizResponse> result = new ArrayList<>();
            for (JsonNode node : quizArray) {
                QuizResponse quiz = new QuizResponse();
                quiz.setQuestion(node.path("question").asText());
                quiz.setOptions(objectMapper.convertValue(node.path("options"), List.class));
                quiz.setAnswer(node.path("answer").asText());
                result.add(quiz);
            }
            return result;
        } catch (Exception e) {
            log.error("GPT 퀴즈 파싱 오류", e);
            return List.of();
        }
    }

    private String callGpt(String prompt) {
        Map<String, Object> requestBody = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                ),
                "temperature", 0.7
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(GPT_API_KEY);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                GPT_URL,
                HttpMethod.POST,
                entity,
                String.class
        );

        return response.getBody();
    }


    private Map<String, Object> parseAnalysisFeedback(String prompt) {
        String gptResponse = callGpt(prompt);
        try {
            String content = objectMapper.readTree(gptResponse)
                    .path("choices").get(0).path("message").path("content").asText();
            JsonNode node = objectMapper.readTree(content);

            String analysis = node.path("analysis").asText();
            List<String> methods = objectMapper.convertValue(node.path("methods"), List.class);
            String scoreSummary = node.path("scoreSummary").asText();

            return Map.of(
                    "analysis", analysis,
                    "methods", methods,
                    "scoreSummary", scoreSummary
            );
        } catch (Exception e) {
            log.error("GPT 피드백 파싱 오류", e);
            return Map.of(
                    "analysis", "성향 분석 실패",
                    "methods", List.of("복습 권장", "영상 시청", "카드 만들기"),
                    "scoreSummary", "점수 요약 실패"
            );
        }
    }

    public QuizSubmitResponse evaluateAllAnswers(QuizSubmitRequest request) {
        List<QuizSubmitFeedback> results = new ArrayList<>();
        int correctCount = 0;

        for (QuizSubmitItem item : request.getSubmissions()) {
            boolean isCorrect = Objects.equals(item.getSelectedAnswer(), item.getCorrectAnswer());
            String feedback = isCorrect
                    ? "정답입니다! 잘하셨어요 🎉"
                    : String.format("오답입니다. 정답은 '%s'입니다.", item.getCorrectAnswer());

            if (isCorrect) correctCount++;
            results.add(new QuizSubmitFeedback(item.getQuestion(), isCorrect, feedback));
        }

        String prompt = buildFeedbackPrompt(request.getSubmissions(), correctCount);
        Map<String, Object> gptResult = parseAnalysisFeedback(prompt);

        int lastScore = 5; // 예시용 하드코딩
        int currentScore = correctCount;
        String progressFeedback = String.format("저번 퀴즈 대비 총 %d점 성장했습니다!", currentScore - lastScore);

        return new QuizSubmitResponse(
                results,
                (String) gptResult.get("analysis"),
                (List<String>) gptResult.get("methods"),
                (String) gptResult.get("scoreSummary"),
                progressFeedback
        );
    }


    private String buildFeedbackPrompt(List<QuizSubmitItem> submissions, int correctCount) {
        StringBuilder sb = new StringBuilder();
        sb.append("다음은 학생의 퀴즈 응답 결과입니다.\n");
        sb.append("정답, 오답을 바탕으로 다음 항목을 작성해주세요:\n");
        sb.append("1. 현재 성향 분석 (1~2문장)\n");
        sb.append("2. 추천 학습 방식 (3개)\n");
        sb.append("3. 정답 수 기반 점수 요약 문장\n");
        sb.append("출력 형식:\n");
        sb.append("{\"analysis\": \"...\", \"methods\": [\"...\", \"...\", \"...\"], \"scoreSummary\": \"...\"}\n\n");

        for (QuizSubmitItem item : submissions) {
            sb.append(String.format("- 질문: %s\n", item.getQuestion()));
            sb.append(String.format("  선택한 답: %s, 정답: %s\n\n",
                    item.getSelectedAnswer(), item.getCorrectAnswer()));
        }

        return sb.toString();
    }


}
