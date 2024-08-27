package com.sparta.plan.service;

import com.sparta.plan.dto.PlanRequestDto;
import com.sparta.plan.dto.PlanResponseDto;
import com.sparta.plan.entity.Plan;
import com.sparta.plan.entity.User;
import com.sparta.plan.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

    private static final int DEFAULT_PAGE_SIZE = 10;

    public PlanResponseDto createPlan(PlanRequestDto requestDto, User user) {

        Plan savePlan = planRepository.save(new Plan(requestDto, user));
        return PlanResponseDto.fromPlanForSingle(savePlan);

    }

    public PlanResponseDto getPlan(Long id) {
        Plan plan = planRepository.findById(id).orElseThrow();
        return PlanResponseDto.fromPlanForSingle(plan);
    }

    public Page<PlanResponseDto> getPlans(PlanRequestDto requestDto, int page, Integer size) {

        int pageSize = (size == null || size <= 0) ? DEFAULT_PAGE_SIZE : size;

        Pageable pageable = PageRequest.of(page, pageSize);

        User user = new User();
        user.setId(requestDto.getUserId());

        Page<Plan> planList = planRepository.findAllByUserOrderByModifiedAtDesc(user, pageable);

        return planList.map(PlanResponseDto::fromPlanForAll);
    }

    public PlanResponseDto updatePlan(Long id, PlanRequestDto requestDto) {
        Plan plan = planRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 일정이 존재하지 않습니다.")
        );

        plan.update(requestDto);

        return PlanResponseDto.fromPlanForSingle(plan);
    }

    public void deletePlan(Long id) {
        Plan plan = planRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 일정이 존재하지 않습니다.")
        );
        planRepository.delete(plan);
    }
}
