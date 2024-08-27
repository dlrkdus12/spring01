package com.sparta.plan.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentRequestDto {
    private String CommentContent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String username;
}
