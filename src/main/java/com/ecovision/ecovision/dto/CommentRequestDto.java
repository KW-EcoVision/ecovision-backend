package com.ecovision.ecovision.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor

public class CommentRequestDto {
    private String content;
}
