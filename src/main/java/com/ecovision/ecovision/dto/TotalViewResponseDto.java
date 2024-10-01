package com.ecovision.ecovision.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TotalViewResponseDto {

    private Long id;

    private int totalDistance;

    private int totalTime;

    private int totalCount;

    private Long userId;
}
