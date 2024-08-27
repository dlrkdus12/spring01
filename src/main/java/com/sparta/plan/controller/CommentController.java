package com.sparta.plan.controller;

import com.sparta.plan.dto.CommentRequestDto;
import com.sparta.plan.dto.CommentResponseDto;
import com.sparta.plan.entity.User;
import com.sparta.plan.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto, User user) {
        return commentService.createComment(requestDto, user);
    }

    @GetMapping("/comments/{id}")
    public CommentResponseDto getComment(@PathVariable Long id) {
        return commentService.getComment(id);
    }

    @GetMapping("/comments")
    public List<CommentResponseDto> getComments() {
        return commentService.getComments();
    }

    @PutMapping("/comments/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestParam String commentContent) {
        return commentService.updateComment(id, commentContent);
    }

    @DeleteMapping("/comments/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

}
