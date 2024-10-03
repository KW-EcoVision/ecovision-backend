package com.ecovision.ecovision.controller;

import com.ecovision.ecovision.dto.PloggingRequestDto;
import com.ecovision.ecovision.dto.PloggingResponseDto;
import com.ecovision.ecovision.dto.PloggingViewResponseDto;
import com.ecovision.ecovision.dto.TotalViewResponseDto;
import com.ecovision.ecovision.service.PloggingService;
import com.ecovision.ecovision.service.TotalViewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/plogging")
public class PloggingController {

    private final PloggingService ploggingService;
    private final TotalViewService totalViewService;
    public PloggingController(PloggingService ploggingService, TotalViewService totalViewService) {
        this.ploggingService = ploggingService;
        this.totalViewService = totalViewService;
    }
    //기록 생성
    @PostMapping("/record")
    public ResponseEntity<?> record(@RequestBody PloggingRequestDto requestDto) {
        PloggingResponseDto responseDto = ploggingService.createPlogging(requestDto);
        return ResponseEntity.ok(responseDto);
    }
    //회차별 기록 조회
    @GetMapping("/view")
    public ResponseEntity<?> view(@RequestParam(name = "id") Long id) {
        try {
            PloggingResponseDto response = ploggingService.ploggingViewById(id);
            return ResponseEntity.ok(response);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    //username -> plgging view list
    @GetMapping("/listview")
    public ResponseEntity<?> listView(@RequestParam(name = "username") String username) {
        try {
            List<PloggingViewResponseDto> response = ploggingService.ploggingListViewByUsername(username);
            return ResponseEntity.ok(response);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    //username -> plogging total view ( 모든 회차 합 )
    @GetMapping("/totalview")
    public ResponseEntity<?> totalView(@RequestParam(name = "username") String username) {
        try {
            TotalViewResponseDto response = totalViewService.ploggingTotalViewByUsername(username);
            return ResponseEntity.ok(response);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
}
