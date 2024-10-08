package com.ecovision.ecovision.controller;

import com.ecovision.ecovision.dto.JoinDto;
import com.ecovision.ecovision.dto.NameUpdateRequestDto;
import com.ecovision.ecovision.dto.PasswordUpdateRequestDto;
import com.ecovision.ecovision.dto.ValiDateDto;
import com.ecovision.ecovision.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@ResponseBody
@RestController
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //회원가입
    @PostMapping("/join")
    public ResponseEntity<?> JoinProcess(@RequestBody JoinDto joinDto) {
        try {
            String response = userService.joinProcess(joinDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/validate-id")
    public ResponseEntity<?> ValiDateProcess(@RequestBody ValiDateDto valiDateDto) {
        try {
            String response = userService.valiDateProcess(valiDateDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
/* 안 쓸 거 같 음
    //유저 정보 조회
    @GetMapping("/userview")
    public ResponseEntity<?> getUserView() {
        try {
            UserResponseDto response = userService.UserView();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
*/
    //유저 이름 수정
    @PutMapping("/update-name")
    public ResponseEntity<?> userNameUpdate(@RequestBody NameUpdateRequestDto nameUpdateRequestDto) {
        try {
            String response = userService.NameUpdateDetails(nameUpdateRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //유저 패스워드 수정
    @PutMapping("/update-password")
    public ResponseEntity<?> userPasswordUpdate(@RequestBody PasswordUpdateRequestDto passwordUpdateRequestDto) {
        try {
            String response = userService.PasswordUpdateDetails(passwordUpdateRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //유저 탈퇴
    @Transactional
    @DeleteMapping("/delete")
    public ResponseEntity<String> userDelete() {
        try {
            userService.userDelete();
            return ResponseEntity.ok("Deleted");
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}


