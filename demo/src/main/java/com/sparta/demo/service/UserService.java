package com.sparta.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sparta.demo.dto.AuthRequestDto;
import com.sparta.demo.entity.User;
import com.sparta.demo.entity.UserRoleEnum;
import com.sparta.demo.jwt.JwtUtil;
import com.sparta.demo.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public void signup(AuthRequestDto requestDto) {
        String username = requestDto.getUsername(); //이름
        String password = passwordEncoder.encode(requestDto.getPassword()); //비밀번호(암호화)
        UserRoleEnum role = requestDto.getRole();   //권한

        if (userRepository.findByUsername(username).isPresent()) {  //중복유저확인
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
        User user = new User(username, password, role);
        userRepository.save(user);
    }

    public void login(AuthRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        //사용자 확인 (username 이 없는 경우)
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        //비밀번호 확인 (password 가 다른 경우)
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}