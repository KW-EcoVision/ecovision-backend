package com.ecovision.ecovision.controller;

import com.ecovision.ecovision.Exception.ResourceNotFoundException;
import com.ecovision.ecovision.dto.BoardRequestDto;
import com.ecovision.ecovision.dto.BoardResponseDto;
import com.ecovision.ecovision.entity.Board;
import com.ecovision.ecovision.entity.User;
import com.ecovision.ecovision.repository.BoardRepository;
import com.ecovision.ecovision.repository.UserRepository;
import com.ecovision.ecovision.service.BoardService;
import com.ecovision.ecovision.service.UserService;
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
@RequestMapping("/board")
@RestController

public class BoardController {
    private final BoardService boardService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;


    // 1. 게시물 등록
    @PostMapping("/post")
    public ResponseEntity<String> saveBoard(@RequestBody BoardRequestDto boardRequestDto) {
        // 인증된 사용자 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User currentUser = userRepository.findByUsername(username);

        if (currentUser == null) {
            throw new ResourceNotFoundException("사용자가 존재하지 않습니다.");
        }

        boardService.saveBoard(currentUser, boardRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("게시물을 생성하였습니다.");
    }


    // 2. 게시물 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> delete(@PathVariable Long boardId) {
        // 인증된 사용자 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User currentUser = userRepository.findByUsername(username);

        if (currentUser == null) {
            throw new ResourceNotFoundException("사용자가 존재하지 않습니다.");
        }

        boardService.deleteBoard(currentUser, boardId);
        return ResponseEntity.status(HttpStatus.OK).body("게시글을 삭제하였습니다.");

    }

    // 3. 게시물 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<String> updateBoard(@PathVariable("boardId") Long boardId, @RequestBody BoardRequestDto boardRequestDto) {
        // 인증된 사용자 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User currentUser = userRepository.findByUsername(username);

        if (currentUser == null) {
            throw new ResourceNotFoundException("사용자가 존재하지 않습니다.");
        }

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("게시글이 존재하지 않습니다."));

        boardService.updateBoard(currentUser, board, boardRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body("게시글을 수정하였습니다.");
    }

    // 4. 게시물 전체 조회
    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> findAllBoard() {
        List<BoardResponseDto> boardResponseDtos = boardService.findAllBoard();

        if (boardResponseDtos.isEmpty()) {
            throw new ResourceNotFoundException("게시물이 존재하지 않습니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(boardResponseDtos);
    }

    // 5. 게시물 상세 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> findByBoardId(@PathVariable Long boardId) {
        BoardResponseDto boardResponseDto = boardService.findByBoardId(boardId);

        if(boardResponseDto == null) {
            throw new ResourceNotFoundException("해당 게시물이 존재하지 않습니다.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(boardResponseDto);
    }


}
