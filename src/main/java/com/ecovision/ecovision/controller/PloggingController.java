package com.ecovision.ecovision.controller;

import com.ecovision.ecovision.dto.*;
import com.ecovision.ecovision.service.PloggingService;
import com.ecovision.ecovision.service.TotalViewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/plogging")
public class PloggingController {


    //의존성 주입
    private final PloggingService ploggingService;
    private final TotalViewService totalViewService;
    public PloggingController(PloggingService ploggingService, TotalViewService totalViewService) {
        this.ploggingService = ploggingService;
        this.totalViewService = totalViewService;
    }

    //기록 생성
    @PostMapping("/record")
    public ResponseEntity<?> record(@RequestBody PloggingRequestDto requestDto) {
        try {
        String response = ploggingService.createPlogging(requestDto);
        totalViewService.createTotalPlogging(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response); //200
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); //404
        }
    }

    //회차별 기록 조회
    @GetMapping("/view/{id}")
    public ResponseEntity<?> view(@PathVariable("id") long id) {
        try {
            PloggingResponseDto response = ploggingService.ploggingViewById(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //유저 기록 리스트 조회
    @GetMapping("/list-view")
    public ResponseEntity<?> listView() {
        try {
            List<PloggingViewResponseDto> response = ploggingService.ploggingListViewByUsername();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //유저 모든 기록 합 조회
    @GetMapping("/total-view")
    public ResponseEntity<?> totalView() {
        try {
            TotalViewResponseDto response = totalViewService.ploggingTotalViewByUsername();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //유저 모든 기록 합 + 기록 리스트 조회
    @GetMapping("/total-and-list-view")
    public ResponseEntity<?> totalAndListView() {
        try {
            TotalViewAndPloggingListDto response = totalViewService.totalViewAndPloggingListByUsername();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
