package com.example.coSSGma.api.controller;

import com.example.coSSGma.api.dto.GroupCreateRequest;
import com.example.coSSGma.api.dto.QuizRequest;
import com.example.coSSGma.api.dto.QuizResponse;
import com.example.coSSGma.api.dto.QuizSubmitRequest;
import com.example.coSSGma.api.dto.QuizSubmitResponse;
import com.example.coSSGma.api.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/quiz/start")
    public ResponseEntity<List<QuizResponse>> startQuiz(@RequestBody QuizRequest request) {
        List<QuizResponse> quizzes = quizService.generateQuizFromContext(request.getContext());
        return ResponseEntity.ok(quizzes);
    }

    @PostMapping("/quiz/submit")
    public ResponseEntity<QuizSubmitResponse> submitAnswers(@RequestBody QuizSubmitRequest request) {
        QuizSubmitResponse response = quizService.evaluateAllAnswers(request);
        return ResponseEntity.ok(response);
    }
}
