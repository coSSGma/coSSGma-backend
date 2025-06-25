package com.example.coSSGma.api.service;


import com.example.coSSGma.api.dto.MatchResultDTO;
import org.springframework.stereotype.Service;

@Service
public class StudyMatchService {

    public MatchResultDTO requestMatch() {
        return new MatchResultDTO(
                "홍길동",
                "20/50",
                "소통형·토론형",
                "PENDING"
        );
    }

    public MatchResultDTO getMatchResult() {
        return new MatchResultDTO(
                "홍길동",
                "20/50",
                "소통형·토론형",
                "PENDING"
        );
    }
}
