package com.sparta.plan.service;

import com.sparta.plan.dto.PlanRequestDto;
import com.sparta.plan.dto.PlanResponseDto;
import com.sparta.plan.entity.Plan;
import com.sparta.plan.entity.User;
import com.sparta.plan.repository.PlanRepository;
import com.sparta.plan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public PlanResponseDto createPlan(PlanRequestDto requestDto) {

        User findUser = userRepository.findById(requestDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
        Plan plan = new Plan(requestDto, findUser);

        planRepository.save(plan);

        return PlanResponseDto.fromPlanForSingle(plan);
    }

    public PlanResponseDto getPlan(Long id) {
        Plan plan = planRepository.findById(id).orElseThrow();
        return PlanResponseDto.fromPlanForSingle(plan);
    }

    public Page<PlanResponseDto> getPlans(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Plan> planList = planRepository.findAllByOrderByModifiedAtDesc(pageable);
        // Page<Plan> -> Page<PlanResponseDto> 바꾸는법
        return planList.map(PlanResponseDto::fromPlanForAll);
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
