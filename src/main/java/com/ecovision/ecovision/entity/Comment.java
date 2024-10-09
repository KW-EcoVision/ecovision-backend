package com.ecovision.ecovision.entity;

// id,boardId,writer,content,writeTime

import com.ecovision.ecovision.dto.CommentResponseDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
@Table(name="comment")

public class Comment {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String writer;

    @Column(nullable=false, length=100)
    private String content;

    @CreationTimestamp
    @Column(nullable=false)
    private LocalDateTime writeTime;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Board board;

    public static Comment toComment(CommentResponseDto commentResponseDto) {
        Comment comment = new Comment();
        comment.setWriter(commentResponseDto.getWriter());
        comment.setContent(commentResponseDto.getContent());
        return comment;

    }


}
