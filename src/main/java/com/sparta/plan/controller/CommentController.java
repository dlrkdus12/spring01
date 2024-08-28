package com.sparta.plan.controller;

import com.sparta.plan.dto.CommentRequestDto;
import com.sparta.plan.dto.CommentResponseDto;
import com.sparta.plan.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api3")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto) {
        return commentService.createComment(requestDto);
    }

    @GetMapping("/comments/{id}")
    public CommentResponseDto getComment(@PathVariable("id") Long id) {
        return commentService.getComment(id);
    }

    @GetMapping("/comments")
    public List<CommentResponseDto> getComments() {
        return commentService.getComments();
    }

    @PutMapping("/comments/{id}")
    public CommentResponseDto updateComment(@PathVariable("id") Long id, @RequestBody CommentRequestDto requestDto) {
        return commentService.updateComment(id, requestDto);
    }

    @DeleteMapping("/comments/{id}")
    public void deleteComment(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
    }

}
