package com.ecovision.ecovision.service;



import com.ecovision.ecovision.dto.*;
import com.ecovision.ecovision.entity.User;
import com.ecovision.ecovision.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
//  중복검사 -> username 넘어오면 boolean is exist -> true or false -> 메세지
    //회원 가입
    public String joinProcess(JoinDto joinDto) {

        String username = joinDto.getUsername();
        String name = joinDto.getName();
        String password = joinDto.getPassword();

        Boolean isExist = userRepository.existsByUsername(username); //아이디 중복 필터

        if(isExist){
            throw new NullPointerException("아이디가 이미 존재합니다.");
        }

        User data = new User();

        data.setUsername(username);
        data.setName(name);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_MEMBER"); // 권한 주기. admin - > member

        userRepository.save(data);

        return "회원가입에 성공했습니다";
    }

    //회원 이름 수정
    public String NameUpdateDetails(NameUpdateRequestDto nameUpdateRequestDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NullPointerException("아이디가 존재하지 않습니다.");
        }

        user.setName(nameUpdateRequestDto.getName());

        return "이름 변경이 완료되었습니다.";
    }

    //회원 패스워드 수정
    public String PasswordUpdateDetails(PasswordUpdateRequestDto passwordUpdateRequestDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NullPointerException("아이디가 존재하지 않습니다.");
        }

        user.setPassword(bCryptPasswordEncoder.encode(passwordUpdateRequestDto.getPassword()));

        return "패스워드 변경이 완료되었습니다.";
    }

    public String valiDateProcess(ValiDateDto valiDateDto) {
        String username = valiDateDto.getUsername();
        Boolean isExist = userRepository.existsByUsername(username);

        if(isExist){
            throw new NullPointerException("아이디가 이미 존재합니다.");
        }

        return "사용가능한 아이디입니다.";
    }

    //유저 삭제
    public void userDelete() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NullPointerException("아이디가 존재하지 않습니다.");
        }
        userRepository.deleteByUsername(username);
    }

/* 안 쓸 거 같 음
    //회원 정보 조회
    public UserResponseDto UserView() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NullPointerException("아이디가 존재하지 않습니다.");
        }

        return new UserResponseDto(user.getId(), user.getUsername(), user.getName(), user.getPassword());
    }
    */

}

