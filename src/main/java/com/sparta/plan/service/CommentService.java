package com.sparta.plan.service;

import com.sparta.plan.dto.CommentRequestDto;
import com.sparta.plan.dto.CommentResponseDto;
import com.sparta.plan.entity.Comment;
import com.sparta.plan.entity.Plan;
import com.sparta.plan.entity.User;
import com.sparta.plan.repository.CommentRepository;
import com.sparta.plan.repository.PlanRepository;
import com.sparta.plan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    public CommentResponseDto createComment(CommentRequestDto requestDto) {

        Plan findPlan = planRepository.findById(requestDto.getPlanId()).orElseThrow(() -> new IllegalArgumentException("플랜 ID가 존재하지 않습니다."));
        User findUser = userRepository.findById(requestDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("유저 ID가 존재하지 않습니다."));

        Comment comment = new Comment(requestDto, findPlan, findUser);
        // Comment 객체를 DB에 저장
        Comment savedComment = commentRepository.save(comment);

        // 저장된 Comment 객체를 사용하여 CommentResponseDto 생성
        return new CommentResponseDto(savedComment);
    }

    public CommentResponseDto getComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow();
        return new CommentResponseDto(comment);
    }

    public List<CommentResponseDto> getComments() {
        List<Comment> totalComment = commentRepository.findAll();
        return totalComment.stream()
                .map(CommentResponseDto::new)
                .toList();
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow();
        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow();
        commentRepository.delete(comment);
    }
}
