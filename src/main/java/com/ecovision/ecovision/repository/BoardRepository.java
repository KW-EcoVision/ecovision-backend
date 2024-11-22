package com.ecovision.ecovision.repository;

import com.ecovision.ecovision.entity.Board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, JpaSpecificationExecutor<Board> {
    @Query("SELECT e FROM Board e ORDER BY e.writeTime DESC")
    List<Board> findAllOrderByCreatedAtDesc();
}
