package com.ecovision.ecovision.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
//total view(sum) + total view(list)
public class TotalViewAndPloggingListDto {

    private Long id;

    private int totalDistance;

    private int totalTime;

    private int totalCount;

    private List<PloggingResponseDto> ploggingList;

}
