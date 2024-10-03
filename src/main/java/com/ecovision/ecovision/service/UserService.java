package com.ecovision.ecovision.service;



import com.ecovision.ecovision.dto.JoinDto;
import com.ecovision.ecovision.entity.User;
import com.ecovision.ecovision.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
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
}
