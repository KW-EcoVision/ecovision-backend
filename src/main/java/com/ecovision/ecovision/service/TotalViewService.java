package com.ecovision.ecovision.service;

import com.ecovision.ecovision.dto.TotalViewResponseDto;
import com.ecovision.ecovision.entity.Plogging;
import com.ecovision.ecovision.entity.TotalView;
import com.ecovision.ecovision.entity.User;
import com.ecovision.ecovision.repository.TotalViewRepository;
import com.ecovision.ecovision.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TotalViewService {
    private final UserRepository userRepository;
    private final TotalViewRepository totalViewRepository;

    public TotalViewService(UserRepository userRepository, TotalViewRepository totalViewRepository) {
        this.userRepository = userRepository;
        this.totalViewRepository = totalViewRepository;
    }

    public TotalViewResponseDto ploggingTotalViewByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NullPointerException("아이디가 존재하지 않습니다.");
        }

        List<Plogging> ploggingList = user.getPloggingEntities();

        int totalDistance = 0;
        int totalTime = 0;
        int totalCount = 0;

        for (Plogging plogging : ploggingList) {
            totalDistance += plogging.getDistance();
            totalTime += plogging.getTime();
            totalCount += plogging.getTrashCount();
        }

        TotalView totalView = totalViewRepository.findByUser(user);
        if (totalView == null) {
            totalView = new TotalView();
            totalView.setUser(user);
        }
        totalView.setTotalDistance(totalDistance);
        totalView.setTotalTime(totalTime);
        totalView.setTotalCount(totalCount);

        totalViewRepository.save(totalView);

        return new TotalViewResponseDto(totalView.getId(), totalDistance, totalTime, totalCount, user.getId());
    }
}
