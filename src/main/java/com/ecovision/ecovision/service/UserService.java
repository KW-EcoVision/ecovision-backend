package com.ecovision.ecovision.service;



import com.ecovision.ecovision.dto.JoinDto;
import com.ecovision.ecovision.dto.UserRequestDto;
import com.ecovision.ecovision.dto.UserResponseDto;
import com.ecovision.ecovision.entity.User;
import com.ecovision.ecovision.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDto joinDto) {

        String username = joinDto.getUsername();
        String password = joinDto.getPassword();

        Boolean isExist = userRepository.existsByUsername(username); //아이디 중복 필터

        if(isExist){  // true(아이디 있으면) -> 아무값 반환 x
            return;
        }

        User data = new User(); //새로운 엔티티

        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password)); //암호화된 패스워드 입력
        data.setRole("ROLE_ADMIN"); //어드민 권한 주기.

        userRepository.save(data);
    }

    public UserResponseDto updateUserDetails(String username, String password, UserRequestDto userRequestDto) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NullPointerException("아이디가 존재하지 않습니다.");
        }

        // 비밀번호 검증
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        user.setName(userRequestDto.getName());
        user.setPassword(bCryptPasswordEncoder.encode(userRequestDto.getPassword()));

        return new UserResponseDto(user.getId(), user.getUsername(), user.getName(), user.getPassword());
    }


    @Transactional
    public void userDelete(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NullPointerException("아이디가 존재하지 않습니다.");
        }

        // 비밀번호 검증
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        userRepository.deleteByUsername(username); // ID로 게시물 삭제
    }
}
