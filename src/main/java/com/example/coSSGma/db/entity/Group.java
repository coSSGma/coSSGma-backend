package com.example.coSSGma.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "study_group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long creatorUserId; // 그룹 생성자 ID

    private String name;
    private String subject;
    private String mode;
    private String description;

    private String memberUserIds; // 예: "1,3,7,10"

    public List<Long> getMemberUserIdList() {
        if (memberUserIds == null || memberUserIds.isBlank()) return List.of();
        return Arrays.stream(memberUserIds.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    public void addMember(Long userId) {
        List<Long> current = getMemberUserIdList();
        if (!current.contains(userId)) {
            current = new ArrayList<>(current);
            current.add(userId);
            memberUserIds = current.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
        }
    }

    public void removeMember(Long userId) {
        List<Long> current = getMemberUserIdList();
        current.remove(userId);
        memberUserIds = current.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }
}
