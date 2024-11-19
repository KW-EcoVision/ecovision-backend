package com.ecovision.ecovision.dto;

import com.ecovision.ecovision.entity.Board;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor

public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    @JsonProperty("write_time")
    private LocalDateTime writeTime;
    @JsonProperty("user_id")
    private Long userId; //외래키

    public static BoardResponseDto toboardDto(Board board) {
        BoardResponseDto boardResponseDto = new BoardResponseDto();
        boardResponseDto.setId(board.getId());
        boardResponseDto.setTitle(board.getTitle());
        boardResponseDto.setContent(board.getContent());
        boardResponseDto.setWriteTime(board.getWriteTime());
        boardResponseDto.setUserId(board.getUser() != null ? board.getUser().getId() : null);

        return boardResponseDto;
    }

}
