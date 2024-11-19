package com.ecovision.ecovision.service;

import com.ecovision.ecovision.Exception.ResourceNotFoundException;
import com.ecovision.ecovision.controller.BoardController;
import com.ecovision.ecovision.dto.BoardRequestDto;
import com.ecovision.ecovision.dto.BoardResponseDto;
import com.ecovision.ecovision.entity.Board;
import com.ecovision.ecovision.entity.User;
import com.ecovision.ecovision.repository.BoardRepository;
import com.ecovision.ecovision.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional

public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 1. 게시글 저장
    public void saveBoard (User currentUser, BoardRequestDto boardRequestDto) {
       User user = userRepository.findById(currentUser.getId())
               .orElseThrow( () -> new ResourceNotFoundException("로그인이 필요합니다."));

       if (!user.getId().equals(currentUser.getId())) {
           throw new AccessDeniedException("접근 권한이 없습니다.");
       }

       User optionalUserEntity = userRepository.findById(currentUser.getId()).orElseThrow(() -> {
           return new ResourceNotFoundException("사용자를 찾을 수 없습니다.");
       } );

       Board board = new Board();
       board.setUser(currentUser);
       board.setContent(boardRequestDto.getContent());
       board.setTitle(boardRequestDto.getTitle());

       boardRepository.save(board);
    }

    // 2. 게시글 전체 조회
    public List<BoardResponseDto> findAllBoard() {
        List<Board> boardList = boardRepository.findAll();

        List<BoardResponseDto> boardDtoList = new ArrayList<>();
        for (Board board : boardList) {
            boardDtoList.add(BoardResponseDto.toboardDto(board));
        }
        return boardDtoList;
    }

    // 3. 게시글 특정 id로 조회
    public BoardResponseDto findByBoardId(Long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();

            BoardResponseDto boardResponseDto = BoardResponseDto.toboardDto(board);
            return boardResponseDto;
        } else {
            return null;
        }

    }


    // 4. 게시글 삭제

    public String deleteBoard(User currentUser, Long boardId) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("로그인이 필요합니다."));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("게시글이 존재하지 않습니다."));

        if (!user.getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        if (board.getUser().getId() == currentUser.getId()) {
            board.getCommentList().clear();
            boardRepository.deleteById(boardId);
            return "ok";
        }
        return null;
    }

    // 5. 게시글 수정
    public void updateBoard(User currentUser, Board board, BoardRequestDto boardRequestDto) {
        if (board.getUser().getId().equals(currentUser.getId())) {

            if (boardRequestDto.getTitle() != null) {
                board.setTitle(boardRequestDto.getTitle());
            }
            if (boardRequestDto.getContent() != null) {
                board.setContent(boardRequestDto.getContent());
            }

        }
    }
}
