package com.sparta.plan.service;

import com.sparta.plan.dto.PlanRequestDto;
import com.sparta.plan.dto.PlanResponseDto;
import com.sparta.plan.entity.Plan;
import com.sparta.plan.repository.PlanRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanService {
    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public PlanResponseDto createPlan(PlanRequestDto requestDto) {
        // dto -> entity
        Plan plan = new Plan(requestDto);
        // entity -> DB
        Plan savePlan = planRepository.save(plan);
        // DB -> entity -> dto
        PlanResponseDto planResponseDto = new PlanResponseDto(plan);
        return planResponseDto;
    }

    public PlanResponseDto getPlan(Long id) {
        Plan plan = planRepository.findById(id);
        PlanResponseDto planResponseDto = new PlanResponseDto(plan);
        return planResponseDto;
    }

    public List<PlanResponseDto> getPlans(String username, PlanRequestDto requestDto) {
        List<PlanResponseDto> plans;

        if (username != null && !username.isEmpty()) {
            plans = planRepository.findByUsername(username);
        } else if (requestDto.getUpdatedDate() != null) {
            plans = planRepository.findAll();
        } else {
            plans = new ArrayList<>();
        }
        // Plan 목록을 PlanResponseDto 목록으로 변환
        return plans.stream()
                .map(plan -> new PlanResponseDto(
                        plan.getToDo(),
                        plan.getUsername(),
                        plan.getPassword(),
                        plan.getCreatedDate() != null ? plan.getCreatedDate() : null,
                        plan.getUpdatedDate() != null ? plan.getUpdatedDate() : null))
                .collect(Collectors.toList());
    }

    public Long updatePlan(Long id, PlanRequestDto requestDto) {
        // 바로 entity 조회 in repository
        Plan plan = planRepository.findById(id);
        plan.update(requestDto);    //4단계 조건
        if (plan != null) {
            planRepository.update(id, plan);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 일정이 존재하지 않습니다.");
        }
    }

    public Long deletePlan(Long id, String password) {
        Plan plan = planRepository.findById(id);
        if (plan != null) {
            planRepository.delete(id, password);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 일정이 존재하지 않습니다.");
        }
    }
}
