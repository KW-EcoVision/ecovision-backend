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
    @JsonProperty("name")
    private String name;

    public static BoardResponseDto toboardDto(Board board) {
        BoardResponseDto boardResponseDto = new BoardResponseDto();
        boardResponseDto.setId(board.getId());
        boardResponseDto.setTitle(board.getTitle());
        boardResponseDto.setContent(board.getContent());
        boardResponseDto.setWriteTime(board.getWriteTime());
        boardResponseDto.setName(board.getUser() != null ? board.getUser().getName() : null);

        return boardResponseDto;
    }

}
