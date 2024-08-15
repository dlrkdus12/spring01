package com.sparta.plan.controller;

import com.sparta.plan.dto.PlanRequestDto;
import com.sparta.plan.dto.PlanResponseDto;
import com.sparta.plan.service.PlanService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @PostMapping("/plans")
    public PlanResponseDto createPlan(@RequestBody PlanRequestDto requestDto) {
        return planService.createPlan(requestDto);
    }

    @GetMapping("/plans/{id}")
    public PlanResponseDto getPlan(@PathVariable(name = "id") Long id) {
        return planService.getPlan(id);
    }

    @GetMapping("/plans")   //parameter-s 에러 뜨면 name ="" 넣기
    public List<PlanResponseDto> getPlans(@RequestParam(required = false, name = "username") String username,
                                          @RequestBody(required = false) PlanRequestDto requestDto) {
        return planService.getPlans(username, requestDto);
    }
    //할일, 담당자명만 수정 가능, 비밀번호 전송
    @PutMapping("/plans/{id}")
    public Long updatePlan(@PathVariable(name = "id") Long id, @RequestBody PlanRequestDto requestDto) {
        return planService.updatePlan(id, requestDto);
    }

    @DeleteMapping("/plans/{id}")
    public Long deletePlan(@PathVariable(name = "id") Long id,
                           @RequestParam(required = false, name = "password") String password) {
        return planService.deletePlan(id, password);
    }
}
