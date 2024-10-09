package com.ecovision.ecovision.service;

import com.ecovision.ecovision.dto.PloggingRequestDto;
import com.ecovision.ecovision.dto.PloggingResponseDto;
import com.ecovision.ecovision.dto.PloggingViewResponseDto;
import com.ecovision.ecovision.entity.Plogging;
import com.ecovision.ecovision.entity.User;
import com.ecovision.ecovision.repository.PloggingRepository;
import com.ecovision.ecovision.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;


@Service
public class PloggingService {

    private final PloggingRepository ploggingRepository;
    private final UserRepository userRepository;

    public PloggingService(PloggingRepository ploggingRepository, UserRepository userRepository) {
        this.ploggingRepository = ploggingRepository;
        this.userRepository = userRepository;
    }

    //기록 생성 서비스
    public PloggingResponseDto createPlogging(PloggingRequestDto ploggingRequestDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username); //token
        if (user == null) {
            throw new NullPointerException("아이디가 존재하지 않습니다.");
        }


        Plogging plogging = new Plogging();
        plogging.setDistance(ploggingRequestDto.getDistance());
        plogging.setTrashCount(ploggingRequestDto.getTrashCount());
        plogging.setTime(ploggingRequestDto.getTime());
        plogging.setLocation(ploggingRequestDto.getLocation());
        plogging.setTimeStamp(LocalDateTime.now());
        plogging.setUser(user);
        ploggingRepository.save(plogging);

        return new PloggingResponseDto(
                plogging.getId(),
                plogging.getDistance(),
                plogging.getTrashCount(),
                plogging.getLocation(),
                plogging.getTime(),
                plogging.getTimeStamp()
        );

    }

    //회차별 기록 조회 (id)
    public PloggingResponseDto ploggingViewById(Long id) {
        Plogging plogging = ploggingRepository.findById(id).orElseThrow(()->new NullPointerException("기록이 존재하지 않습니다."));
        return new PloggingResponseDto(
                plogging.getId(),
                plogging.getDistance(),
                plogging.getTrashCount(),
                plogging.getLocation(),
                plogging.getTime(),
                plogging.getTimeStamp()
        );
    }

    //모든 기록 list 조회
    public List<PloggingViewResponseDto> ploggingListViewByUsername() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username); //token
        if (user == null) {
            throw new NullPointerException("아이디가 존재하지 않습니다.");
        }

        List<Plogging> ploggingList = user.getPloggingEntities();
        if (ploggingList == null) {
            throw new NullPointerException("기록이 존재하지 않습니다.");
        }
        List<PloggingViewResponseDto> responseDtoList = new ArrayList<>();

        for (Plogging plogging : ploggingList) {
            PloggingViewResponseDto responseDto = new PloggingViewResponseDto(
                    plogging.getId(),
                    plogging.getDistance(),
                    plogging.getTrashCount(),
                    plogging.getLocation(),
                    plogging.getTime(),
                    plogging.getTimeStamp()
            );
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }



};