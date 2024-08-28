package com.sparta.plan.service;

import com.sparta.plan.dto.UserRequestDto;
import com.sparta.plan.dto.UserResponseDto;
import com.sparta.plan.entity.User;
import com.sparta.plan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto createUser(UserRequestDto requestDto) {
        User saveUser = userRepository.save(new User(requestDto));
        return new UserResponseDto(saveUser);
    }

    public UserResponseDto getUser(Long userId) {
         User findUser = userRepository.findById(userId).orElseThrow(() ->
                 new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
         );
         return new UserResponseDto(findUser);
    }


    public List<UserResponseDto> getUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(UserResponseDto::new).toList();
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
    }
}
