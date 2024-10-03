package com.ecovision.ecovision.controller;

import com.ecovision.ecovision.dto.JoinDto;
import com.ecovision.ecovision.dto.UserRequestDto;
import com.ecovision.ecovision.dto.UserResponseDto;
import com.ecovision.ecovision.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;

@ResponseBody
@RestController
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    //회원가입
    @PostMapping("/join")
    public String JoinProcess(JoinDto joinDto) {

        userService.joinProcess(joinDto);

        return "회원가입에 성공하셨습니다.";
    }

    //역할
    @GetMapping("/admin")
    public String adminP() {

        return "Admin";
    }

    @GetMapping("/")
    public String mainP() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        return "Main Controller : " + username + role;

    }

    //유저 정보 수정
    @PutMapping("/update")
    public ResponseEntity<?> userUpdate(@RequestParam(name = "username") String username, String password, @RequestBody UserRequestDto userRequestDto) {
        try {
            UserResponseDto response = userService.updateUserDetails(username, password, userRequestDto);
            return ResponseEntity.ok(response);
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //유저 탈퇴
    @PutMapping("/delete")
    public ResponseEntity<String> userDelete(@RequestParam(name = "username") String username, String password) {
        userService.userDelete(username, password);
        return ResponseEntity.ok("Deleted");
    }



}
