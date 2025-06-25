package com.example.coSSGma.api.controller;


import com.example.coSSGma.api.dto.MatchResultDTO;
import com.example.coSSGma.api.service.StudyMatchService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/match")
public class StudyMatchController {
    private final StudyMatchService studyMatchService;

    public StudyMatchController(StudyMatchService studyMatchService) {
        this.studyMatchService = studyMatchService;
    }

    @PostMapping("/request")
    public ResponseEntity<MatchResultDTO> requestMatch() {
        MatchResultDTO result = studyMatchService.requestMatch();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/result")
    public ResponseEntity<MatchResultDTO> getMatchResult() {
        MatchResultDTO result = studyMatchService.getMatchResult();
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}
