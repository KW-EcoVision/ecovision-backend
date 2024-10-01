package com.ecovision.ecovision.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PloggingRequestDto {

    private int distance;

    private int trashCount;

    private String location;

    private int time;

    private Long userId;

}
