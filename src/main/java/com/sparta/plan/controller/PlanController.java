package com.sparta.plan.controller;

import com.sparta.plan.dto.PlanRequestDto;
import com.sparta.plan.dto.PlanResponseDto;
import com.sparta.plan.entity.User;
import com.sparta.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<PlanResponseDto>> getPlans(
            @RequestParam(required = false, name = "page") int page,
            @RequestParam(required = false, name = "size", defaultValue = "10") int size,
            @RequestParam(required = false, name = "requestDto") PlanRequestDto requestDto) {
        List<PlanResponseDto> plans = planService.getPlans(requestDto, page, size);
        return ResponseEntity.ok(plans);
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
