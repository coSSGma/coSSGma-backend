package com.example.coSSGma.api.dto;

import lombok.Data;

@Data
public class GroupCreateRequest {
    private Long creatorUserId;
    private String name;
    private String subject;
    private String mode; // "일반" or "게임"
    private String description;
}