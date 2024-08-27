package com.sparta.plan.repository;

import com.sparta.plan.entity.Plan;
import com.sparta.plan.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

    Page<Plan> findAllByUserOrderByModifiedAtDesc(User user, Pageable pageable);
}
