package com.ecovision.ecovision.service;

import com.ecovision.ecovision.Exception.CommentException;
import com.ecovision.ecovision.Exception.ResourceNotFoundException;
import com.ecovision.ecovision.dto.CommentRequestDto;
import com.ecovision.ecovision.dto.CommentResponseDto;
import com.ecovision.ecovision.entity.Board;
import com.ecovision.ecovision.entity.Comment;
import com.ecovision.ecovision.entity.User;
import com.ecovision.ecovision.repository.BoardRepository;
import com.ecovision.ecovision.repository.CommentRepository;
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

public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userservice;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    // 1. 댓글 저장
    @Transactional
    public void saveComment(User currentUser, CommentRequestDto commentRequestDto) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> { return new ResourceNotFoundException("사용자를 찾을 수 없습니다.");
                });

        if (!user.getId().equals(currentUser.getId())) {
            throw new CommentException("접근 권한이 없습니다.");
        }

        Board optionalBoardEntity = boardRepository.findById(commentRequestDto.getBoardId())
                .orElseThrow(() -> { return new ResourceNotFoundException("게시물을 찾을 수 없습니다.");
         });

        Comment comment = Comment.builder()
                .writer(currentUser.getName())
                .content(commentRequestDto.getContent())
                .board(optionalBoardEntity)
                .build();
        commentRepository.save(comment);

    }

    // 2. 댓글 삭제
    @Transactional
    public void deleteComment(User currentUser, Long id) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("로그인이 필요합니다."));

        if(!user.getId().equals(currentUser.getId())){
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }
        commentRepository.deleteById(id);
    }


    // 3. ID로 댓글 찾기
    @Transactional
    public CommentResponseDto findCommentById(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            CommentResponseDto commentResponseDto = CommentResponseDto.toCommentDto(comment);
            return commentResponseDto;
        } else {
            return null;
        }
    }

    // 4. 댓글 목록 조회
    @Transactional
    public List<CommentResponseDto> findAllComment(Long boardId) {
        Board optionalBoardEntity = boardRepository.findById(boardId)
                .orElseThrow(() -> { return new ResourceNotFoundException("게시글이 존재하지 않습니다.");
        });

        Board board = boardRepository.findById(boardId).get();
        List<Comment> commentList = commentRepository.findAllByBoardOrderByIdDesc(board);

        List<CommentResponseDto> commentDtoList = new ArrayList<>();
        for (Comment comment: commentList) {
            CommentResponseDto commentResponseDto = CommentResponseDto.toCommentDto(comment);
            commentDtoList.add(commentResponseDto);
        }
        return commentDtoList;

    }
}
