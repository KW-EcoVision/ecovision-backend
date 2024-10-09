package com.ecovision.ecovision.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
//플로깅 기록 조회 dto -> 사실상 plogging response dto 랑 같음
public class PloggingViewResponseDto {


    private Long id;

    private int distance;

    private int trashCount;

    private String location;

    private int time;

    private LocalDateTime timeStamp;

};
