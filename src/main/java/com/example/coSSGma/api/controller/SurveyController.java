package com.example.coSSGma.api.controller;


import com.example.coSSGma.api.dto.SurveyResponseDTO;
import com.example.coSSGma.api.dto.SurveyResultDTO;
import com.example.coSSGma.api.service.SurveyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sbti-test")
public class SurveyController {
    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping("/submit")
    public ResponseEntity<SurveyResultDTO> submitSurvey(@RequestBody SurveyResponseDTO dto) {
        SurveyResultDTO result = surveyService.submitSurvey(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/result")
    public ResponseEntity<SurveyResultDTO> getSurveyResult(@RequestParam Long userId) {
        SurveyResultDTO result = surveyService.getSurveyResult(userId);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}
