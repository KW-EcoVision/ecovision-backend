package com.ecovision.ecovision.repository;

import com.ecovision.ecovision.entity.Board;
import com.ecovision.ecovision.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoardOrderByIdDesc(Board board);
}
