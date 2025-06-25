package com.example.coSSGma.api.service;

import com.example.coSSGma.db.entity.Timer;
import com.example.coSSGma.db.repository.TimerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TimerService {

    private final TimerRepository timerRepository;

    public void startSession(Long userId) {
        Timer session = new Timer();
        session.setUserId(userId);
        session.setStartTime(LocalDateTime.now());

        timerRepository.save(session);
    }

    public long endSession(Long userId) {
        Timer session = timerRepository.findTopByUserIdOrderByStartTimeDesc(userId)
                .orElseThrow(() -> new RuntimeException("시작된 타이머 세션이 없습니다."));

        LocalDateTime end = LocalDateTime.now();
        session.setEndTime(end);

        log.info("시작시간: {}", session.getStartTime());
        log.info("종료시간: {}", end);
        long seconds = Duration.between(session.getStartTime(), end).getSeconds();
        session.setDurationInMinutes(seconds);

        timerRepository.save(session);

        return seconds;
    }
}
