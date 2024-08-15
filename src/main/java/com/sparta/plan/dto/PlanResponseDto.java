package com.sparta.plan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.plan.entity.Plan;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PlanResponseDto {
    private String toDo;
    private String username;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime updatedDate;


    public PlanResponseDto(String toDo, String username, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.toDo = toDo;
        this.username = username;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public PlanResponseDto(Plan plan) {
        this.toDo = plan.getToDo();
        this.username = plan.getUsername();
        this.createdDate = plan.getCreatedDate();
        this.updatedDate = plan.getUpdatedDate();
    }


}
