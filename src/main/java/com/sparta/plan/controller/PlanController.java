package com.sparta.plan.controller;

import com.sparta.plan.dto.PlanRequestDto;
import com.sparta.plan.dto.PlanResponseDto;
import com.sparta.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api1")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @PostMapping("/plans")
    public PlanResponseDto createPlan(@RequestBody PlanRequestDto requestDto) {
        return planService.createPlan(requestDto);
    }

    @GetMapping("/plans/{id}")
    public PlanResponseDto getPlan(@PathVariable(name = "id") Long id) {
        return planService.getPlan(id);
    }

    @GetMapping("/plans")   //parameter-s 에러 뜨면 name ="" 넣기
    public Page<PlanResponseDto> getPlans(
            @RequestParam(value = "page", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        return planService.getPlans(page-1, size);
    }

    @PutMapping("/plans/{id}")
    public PlanResponseDto updatePlan(@PathVariable("id") Long id, @RequestBody PlanRequestDto requestDto) {
        return planService.updatePlan(id, requestDto);
    }

    @DeleteMapping("/plans/{id}")
    public void deletePlan(@PathVariable(name = "id") Long id) {
        planService.deletePlan(id);
    }
}
