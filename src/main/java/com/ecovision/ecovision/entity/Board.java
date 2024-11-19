package com.ecovision.ecovision.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="board")
@Getter @Setter

public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=100)
    private String title;

    @Column(nullable=false, length=3000)
    private String content;

    @CreationTimestamp
    @Column(nullable=false)
    private LocalDateTime writeTime;


    // board -> comment
    @OneToMany(mappedBy= "board", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>();

    // user -> board
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name= "user_id", nullable=false)
    @OnDelete(action= OnDeleteAction.CASCADE)
    private User user;


}
