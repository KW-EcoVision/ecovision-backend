package com.ecovision.ecovision.service;

import com.ecovision.ecovision.repository.TotalViewRepository;
import org.springframework.stereotype.Service;

@Service
public class TotalViewService {

    private final TotalViewRepository totalViewRepository;
    public TotalViewService(TotalViewRepository totalViewRepository) {
        this.totalViewRepository = totalViewRepository;
    }

}
