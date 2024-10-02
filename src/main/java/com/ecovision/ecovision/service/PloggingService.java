package com.ecovision.ecovision.service;

import com.ecovision.ecovision.dto.PloggingRequestDto;
import com.ecovision.ecovision.dto.PloggingResponseDto;
import com.ecovision.ecovision.entity.Plogging;
import com.ecovision.ecovision.entity.User;
import com.ecovision.ecovision.repository.PloggingRepository;
import com.ecovision.ecovision.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class PloggingService {

    private final PloggingRepository ploggingRepository;
    private final UserRepository userRepository; // UserRepository 추가

    public PloggingService(PloggingRepository ploggingRepository, UserRepository userRepository) {
        this.ploggingRepository = ploggingRepository;
        this.userRepository = userRepository; // UserRepository 초기화
    }
    public PloggingResponseDto createPlogging(PloggingRequestDto ploggingRequestDto) {
        // 유저 찾기
        User user = userRepository.findById(ploggingRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저 ID 가 존재하지않습니다."));

        Plogging plogging = new Plogging();
        plogging.setDistance(ploggingRequestDto.getDistance());
        plogging.setTrashCount(ploggingRequestDto.getTrashCount());
        plogging.setTime(ploggingRequestDto.getTime());
        plogging.setLocation(ploggingRequestDto.getLocation());
        plogging.setTimeStamp(LocalDateTime.now());
        plogging.setUser(user); // 유저 설정
        ploggingRepository.save(plogging);

        return new PloggingResponseDto(
                plogging.getId(),
                plogging.getDistance(),
                plogging.getTrashCount(),
                plogging.getLocation(),
                plogging.getTime(),
                plogging.getTimeStamp(),
                user.getId() // 유저 ID 포함
        );

    }

}

