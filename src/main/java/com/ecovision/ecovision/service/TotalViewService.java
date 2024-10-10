package com.ecovision.ecovision.service;

import com.ecovision.ecovision.dto.*;
import com.ecovision.ecovision.entity.Plogging;
import com.ecovision.ecovision.entity.TotalView;
import com.ecovision.ecovision.entity.User;
import com.ecovision.ecovision.repository.TotalViewRepository;
import com.ecovision.ecovision.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class TotalViewService {
    private final UserRepository userRepository;
    private final TotalViewRepository totalViewRepository;
    public TotalViewService(UserRepository userRepository, TotalViewRepository totalViewRepository) {
        this.userRepository = userRepository;
        this.totalViewRepository = totalViewRepository;
    }

    //기록 생성 controller -> 기록 생성 할 때 마다 (total view +=) 저장
    public TotalViewResponseDto createTotalPlogging(PloggingRequestDto requestDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NullPointerException("아이디가 존재하지 않습니다.");
        }
        TotalView totalView = totalViewRepository.findByUser(user);
        if (totalView == null) {
            totalView = new TotalView();
            totalView.setUser(user);
        }

        totalView.setTotalDistance(totalView.getTotalDistance() + requestDto.getDistance());
        totalView.setTotalTime(totalView.getTotalTime() + requestDto.getTime());
        totalView.setTotalCount(totalView.getTotalCount() + requestDto.getTrashCount());

        totalViewRepository.save(totalView);

        return new TotalViewResponseDto(
                totalView.getId(),
                totalView.getTotalDistance(),
                totalView.getTotalTime(),
                totalView.getTotalCount()
        );

    }


    //total view(sum)
    public TotalViewResponseDto ploggingTotalViewByUsername() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NullPointerException("아이디가 존재하지 않습니다.");
        }

        TotalView totalView = totalViewRepository.findByUser(user);
        if (totalView == null) {
            throw new NullPointerException("기록이 존재하지 않습니다.");
        }

        return new TotalViewResponseDto(
                totalView.getId(),
                totalView.getTotalDistance(),
                totalView.getTotalTime(),
                totalView.getTotalCount()
        );


    }

    //total view(list) + total view (sum)
    public TotalViewAndPloggingListDto totalViewAndPloggingListByUsername() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NullPointerException("아이디가 존재하지 않습니다.");
        }

        TotalView totalView = totalViewRepository.findByUser(user);
        if (totalView == null) {
            throw new NullPointerException("기록이 존재하지 않습니다.");
        }

        List<Plogging> ploggingList = user.getPloggingEntities();
        List<PloggingResponseDto> ploggingResponseList = new ArrayList<>();
        for (Plogging plogging : ploggingList) {
            PloggingResponseDto ploggingResponseDto = new PloggingResponseDto(
                    plogging.getId(),
                    plogging.getDistance(),
                    plogging.getTrashCount(),
                    plogging.getLocation(),
                    plogging.getTime(),
                    plogging.getTimeStamp()
            );
            ploggingResponseList.add(ploggingResponseDto);
        }

        return new TotalViewAndPloggingListDto(
                totalView.getId(),
                totalView.getTotalDistance(),
                totalView.getTotalTime(),
                totalView.getTotalCount(),
                ploggingResponseList
        );
    }
};
