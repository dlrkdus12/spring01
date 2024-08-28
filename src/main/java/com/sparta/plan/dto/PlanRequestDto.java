package com.sparta.plan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlanRequestDto {

    private Long userId;
    private String title;
    private String content;

}
