package com.example.coSSGma.api.service;


public enum SBTIType {
    INDEPENDENT_PLANNER(
            "혼공 집중러",
            "혼자 공부하는 것을 선호하며, 세부적인 계획을 세워 체계적으로 학습",
            "독립적인 학습을 선호하는 소규모 스터디 그룹"
    ),
    COLLABORATIVE_LEADER(
            "토론형 집중러",
            "그룹에서 토론 위주의 학습을 즐기며, 소통과 협업을 중시",
            "활발한 토론 중심의 그룹, 실습 위주 과목"
    ),
    FLEXIBLE_FREETHINKER(
            "유연한 자유인",
            "계획보다는 즉흥적으로 공부하며, 창의적인 방식으로 학습",
            "유연한 일정의 그룹, 게임 모드(타임 어택) 선호"
    ),
    GOAL_ORIENTED_ACHIEVER(
            "목표 지향자",
            "성적과 같은 명확한 목표를 추구하며, 성취감으로 동기부여",
            "성적 향상에 초점 맞춘 그룹, 계획적인 스터디"
    ),
    EMPATHETIC_COMMUNICATOR(
            "탐구형 리서처",
            "이론과 배경 지식 탐색을 즐기는 분석형",
            "자료조사, 발표 준비 중심의 탐색형 스터디"
    ),
    FLEXIBLE_NIGHT_OWL(
            "유연형 밤샘러",
            "늦은 시간대에 집중력이 높으며, 유연한 스케줄로 학습",
            "새벽 시간대 스터디, 유연한 일정의 그룹"
    );

    private final String name;
    private final String trait;
    private final String match;

    SBTIType(String name, String trait, String match) {
        this.name = name;
        this.trait = trait;
        this.match = match;
    }

    public String getName() {
        return name;
    }

    public String getTrait() {
        return trait;
    }

    public String getMatch() {
        return match;
    }

    // SBTI 코드로 enum 조회
    public static SBTIType fromCode(String code) {
        for (SBTIType type : values()) {
            if (type.name().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid SBTI code: " + code);
    }
}
