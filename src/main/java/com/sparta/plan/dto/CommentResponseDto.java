package com.sparta.plan.dto;

import com.sparta.plan.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private String CommentContent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String username;

    public CommentResponseDto(Comment comment) {
        this.CommentContent = comment.getCommentContent();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getModifiedAt();
        this.username = comment.getUser().getUsername();
    }
}
