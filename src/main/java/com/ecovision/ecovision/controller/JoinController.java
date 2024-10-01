package com.ecovision.ecovision.controller;


import com.ecovision.ecovision.dto.JoinDto;
import com.ecovision.ecovision.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class JoinController {

    private final JoinService joinService;
    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @PostMapping("/join")
    public String JoinProcess(JoinDto joinDto) {

        joinService.joinProcess(joinDto);

        return "회원가입에 성공하셨습니다.";
    };
}
