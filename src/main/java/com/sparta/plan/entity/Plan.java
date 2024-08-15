package com.sparta.plan.entity;

import com.sparta.plan.dto.PlanRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Plan {
    private Long id;
    private String toDo;
    private String username;
    private String password;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public Plan(PlanRequestDto requestDto) {
        this.toDo = requestDto.getToDo();
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.createdDate = requestDto.getCreatedDate();
        this.updatedDate = requestDto.getCreatedDate();
    }

    public void update(PlanRequestDto requestDto) {
        this.toDo = requestDto.getToDo();
        this.username = requestDto.getUsername();
        this.updatedDate = requestDto.getUpdatedDate();
    }
}
