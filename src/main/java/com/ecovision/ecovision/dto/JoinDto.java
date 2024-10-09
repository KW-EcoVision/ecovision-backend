package com.ecovision.ecovision.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//회원 가입 request dto
public class JoinDto {


    private String username;

    private String name;

    private String password;
};
