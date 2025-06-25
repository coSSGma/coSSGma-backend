package com.example.coSSGma.db.repository;

import com.example.coSSGma.db.entity.Timer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TimerRepository extends JpaRepository<Timer, Long> {
    Optional<Timer> findTopByUserIdOrderByStartTimeDesc(Long userId);
}
