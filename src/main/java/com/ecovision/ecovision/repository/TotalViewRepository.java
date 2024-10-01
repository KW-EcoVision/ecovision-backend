package com.ecovision.ecovision.repository;

import com.ecovision.ecovision.entity.TotalView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TotalViewRepository extends JpaRepository<TotalView, Long>{}
