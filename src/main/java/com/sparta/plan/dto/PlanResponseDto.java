package com.sparta.plan.dto;

import com.sparta.plan.entity.Plan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PlanResponseDto {
    private String title;
    private String content;
    private int commentSize;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long userId;
    private String email;
    private String username; // 단건 조회 시 포함될 필드

    // 전체 조회용 정적 팩토리 메서드
    public static PlanResponseDto fromPlanForAll(Plan plan) {
        return PlanResponseDto.builder()
                .title(plan.getTitle())
                .content(plan.getContent())
                .commentSize(plan.getCommentList().size())
                .createdAt(plan.getCreatedAt())
                .updatedAt(plan.getModifiedAt())
                .userId(plan.getUser().getId())
                .email(plan.getUser().getEmail())
                .build();
    }

    // 단건 조회용 정적 팩토리 메서드
    public static PlanResponseDto fromPlanForSingle(Plan plan) {
        return PlanResponseDto.builder()
                .title(plan.getTitle())
                .content(plan.getContent())
                .commentSize(plan.getCommentList().size())
                .createdAt(plan.getCreatedAt())
                .updatedAt(plan.getModifiedAt())
                .userId(plan.getUser().getId())
                .email(plan.getUser().getEmail())
                .username(plan.getUser().getUsername()) // 단건 조회 시에만 추가
                .build();
    }
}
