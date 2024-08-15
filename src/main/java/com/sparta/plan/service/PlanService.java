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
                        plan.getCreatedDate() != null ? plan.getCreatedDate() : null,
                        plan.getUpdatedDate() != null ? plan.getUpdatedDate() : null))
                .collect(Collectors.toList());
    }

    public Long updatePlan(Long id, PlanRequestDto requestDto) {
        // Plan 엔티티 조회
        Plan plan = planRepository.findById(id);

        if (plan == null) {
            throw new IllegalArgumentException("선택한 일정이 존재하지 않습니다.");
        }

        // 비밀번호 일치 여부 검사
        if (!plan.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        plan.update(requestDto);
        planRepository.update(id, plan);

        return id;
    }

    public Long deletePlan(Long id, String password) {
        Plan plan = planRepository.findById(id);

        if (plan == null) {
            throw new IllegalArgumentException("선택한 일정이 존재하지 않습니다.");
        }

        // 비밀번호 일치 여부 검사
        if (!plan.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        planRepository.delete(id, password);
        return id;
    }
}
