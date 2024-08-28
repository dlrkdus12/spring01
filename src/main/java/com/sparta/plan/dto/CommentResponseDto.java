package com.sparta.plan.dto;

import com.sparta.plan.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentResponseDto {
    private String commentContent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;

    public CommentResponseDto(Comment comment) {
        this.commentContent = comment.getCommentContent();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getModifiedAt();
        this.userId = comment.getUser().getId();
    }
}
