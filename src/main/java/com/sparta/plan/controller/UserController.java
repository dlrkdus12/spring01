package com.sparta.plan.controller;

import com.sparta.plan.dto.UserRequestDto;
import com.sparta.plan.dto.UserResponseDto;
import com.sparta.plan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api2")
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public UserResponseDto createUser(@RequestBody UserRequestDto requestDto) {
        return userService.createUser(requestDto);
    }

    @GetMapping("/users/{id}")
    public UserResponseDto getUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/users")
    public List<UserResponseDto> getUsers() {
        return userService.getUsers();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

}
