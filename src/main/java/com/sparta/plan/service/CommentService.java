package com.sparta.plan.service;

import com.sparta.plan.dto.CommentRequestDto;
import com.sparta.plan.dto.CommentResponseDto;
import com.sparta.plan.entity.Comment;
import com.sparta.plan.entity.User;
import com.sparta.plan.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentResponseDto createComment(CommentRequestDto requestDto, User user) {

        Comment saveComment = commentRepository.save(new Comment(requestDto, user));
        return new CommentResponseDto(saveComment);
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

    public CommentResponseDto updateComment(Long id, String commentContent) {
        Comment comment = commentRepository.findById(id).orElseThrow();
        comment.update(commentContent);
        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow();
        commentRepository.delete(comment);
    }
}
