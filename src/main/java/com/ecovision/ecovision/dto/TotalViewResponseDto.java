package com.ecovision.ecovision.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
//totalView (sum) response dto
public class TotalViewResponseDto {

    private Long id;

    private int totalDistance;

    private int totalTime;

    private int totalCount;

};
