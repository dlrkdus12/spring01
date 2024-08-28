package com.sparta.plan.service;

import com.sparta.plan.dto.PlanRequestDto;
import com.sparta.plan.dto.PlanResponseDto;
import com.sparta.plan.entity.Plan;
import com.sparta.plan.entity.User;
import com.sparta.plan.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

    public PlanResponseDto createPlan(PlanRequestDto requestDto, User user) {

        Plan savePlan = planRepository.save(new Plan(requestDto, user));
        return PlanResponseDto.fromPlanForSingle(savePlan);

    }

    public PlanResponseDto getPlan(Long id) {
        Plan plan = planRepository.findById(id).orElseThrow();
        return PlanResponseDto.fromPlanForSingle(plan);
    }

    public List<PlanResponseDto> getPlans(PlanRequestDto requestDto, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return planRepository.findAllByUserOrderByModifiedAtDesc(requestDto.getUserId(), pageable)
                .stream()
                .map(PlanResponseDto::fromPlanForSingle)
                .collect(Collectors.toList());
    }

    @Transactional
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
