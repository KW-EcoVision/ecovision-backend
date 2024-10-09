package com.ecovision.ecovision.dto;

import com.ecovision.ecovision.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor

public class CommentResponseDto {
    private Long id;
    private String writer;
    private String content;
    @JsonProperty("write_time")
    private LocalDateTime writeTime;
    @JsonProperty("board_id")
    private Long boardId; //외래키

    public static CommentResponseDto toCommentDto(Comment comment) {
        CommentResponseDto commentResponseDto = new CommentResponseDto();
        commentResponseDto.setId(comment.getId());
        commentResponseDto.setWriter(comment.getWriter());
        commentResponseDto.setContent(comment.getContent());
        commentResponseDto.setWriteTime(comment.getWriteTime());
        commentResponseDto.setBoardId(comment.getBoard() != null ? comment.getBoard().getId() : null);
        return commentResponseDto;

    }

}
