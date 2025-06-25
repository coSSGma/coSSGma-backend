package com.example.coSSGma.api.controller;

import com.example.coSSGma.api.service.TimerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TimerController {

    private final TimerService timerService;

    @PostMapping("/start")
    public ResponseEntity<String> startTimer(@RequestParam Long userId) {
        timerService.startSession(userId);
        return ResponseEntity.ok("타이머를 시작했습니다.");
    }

    @PostMapping("/end")
    public ResponseEntity<Long> endTimer(@RequestParam Long userId) {
        long duration = timerService.endSession(userId);
        return ResponseEntity.ok(duration);
    }
}

