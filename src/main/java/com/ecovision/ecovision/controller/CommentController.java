package com.ecovision.ecovision.controller;

import com.ecovision.ecovision.Exception.ResourceNotFoundException;
import com.ecovision.ecovision.dto.BoardRequestDto;
import com.ecovision.ecovision.dto.CommentRequestDto;
import com.ecovision.ecovision.dto.CommentResponseDto;
import com.ecovision.ecovision.entity.User;
import com.ecovision.ecovision.repository.CommentRepository;
import com.ecovision.ecovision.repository.UserRepository;
import com.ecovision.ecovision.service.CommentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter @Setter @RequiredArgsConstructor
@RequestMapping("/comment")
@RestController

public class CommentController {
    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    // 1. 댓글 등록
    @PostMapping("/post")
    public ResponseEntity<String> saveComment(@RequestBody CommentRequestDto commentRequestDto) {
        // 인증된 사용자 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userRepository.findByUsername(username);

        if (currentUser == null) {
            throw new ResourceNotFoundException("사용자가 존재하지 않습니다.");
        }

        commentService.saveComment(currentUser,commentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("댓글이 등록되었습니다.");
    }

    // 2. 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        // 인증된 사용자 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userRepository.findByUsername(username);

        if (currentUser == null) {
            throw new ResourceNotFoundException("사용자가 존재하지 않습니다.");
        }

        commentService.deleteComment(currentUser,commentId);
        return ResponseEntity.status(HttpStatus.OK).body("댓글이 삭제되었습니다.");
    }

    // 3. 댓글 전체 목록 조회
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findAllComment(@RequestBody CommentRequestDto commentRequestDto) {
        List<CommentResponseDto> commentResponseDtos = commentService.findAllComment(commentRequestDto.getBoardId());

        if (commentResponseDtos.isEmpty()){
            throw new ResourceNotFoundException("댓글이 존재하지 않습니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDtos);
    }

    // 4. 댓글 ID로 조회
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> findCommentById(@PathVariable Long commentId) {
        CommentResponseDto commentResponseDto = commentService.findCommentById(commentId);

        if (commentResponseDto == null){
            throw new ResourceNotFoundException("해당 댓글이 존재하지 않습니다");
        }
        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDto);
        }
    }

