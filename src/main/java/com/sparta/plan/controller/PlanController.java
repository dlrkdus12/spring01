package com.sparta.plan.controller;

import com.sparta.plan.dto.PlanRequestDto;
import com.sparta.plan.dto.PlanResponseDto;
import com.sparta.plan.entity.User;
import com.sparta.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @PostMapping("/plans")
    public PlanResponseDto createPlan(@RequestBody PlanRequestDto requestDto, User user) {
        return planService.createPlan(requestDto, user);
    }

    @GetMapping("/plans/{id}")
    public PlanResponseDto getPlan(@PathVariable(name = "id") Long id) {
        return planService.getPlan(id);
    }

    @GetMapping("/plans")   //parameter-s 에러 뜨면 name ="" 넣기
    public Page<PlanResponseDto> getPlans(
            @RequestParam(required = false, name = "page") int page,
            @RequestParam(required = false, name = "size") Integer size,
            PlanRequestDto requestDto
            ) {
        return planService.getPlans(requestDto,page-1, size);
    }

    @PutMapping("/plans/{id}")
    public PlanResponseDto updatePlan(@PathVariable(name = "id") Long id, @RequestBody PlanRequestDto requestDto) {
        return planService.updatePlan(id, requestDto);
    }

    @DeleteMapping("/plans/{id}")
    public void deletePlan(@PathVariable(name = "id") Long id) {
        planService.deletePlan(id);
    }
}
