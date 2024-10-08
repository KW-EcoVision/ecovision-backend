package com.ecovision.ecovision.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
//User 정보 조회 등등
public class UserResponseDto {

    private Long id;

    private String username;

    private String name;

    private String password;


}
