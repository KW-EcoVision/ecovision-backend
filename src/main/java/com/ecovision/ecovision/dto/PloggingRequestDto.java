package com.ecovision.ecovision.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
//plogging 기록 생성 request dto
public class PloggingRequestDto {

    private int distance;

    private int trashCount;

    private String location;

    private int time;

};
