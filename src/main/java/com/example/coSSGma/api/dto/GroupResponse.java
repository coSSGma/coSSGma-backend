package com.example.coSSGma.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupResponse {
    private Long groupId;
    private String name;
    private String subject;
    private String mode;
    private String description;
    private int memberCount;
}