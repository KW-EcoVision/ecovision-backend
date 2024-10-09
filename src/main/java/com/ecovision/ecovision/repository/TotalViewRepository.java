package com.ecovision.ecovision.repository;

import com.ecovision.ecovision.entity.TotalView;
import com.ecovision.ecovision.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//TotalViewRepository
public interface TotalViewRepository extends JpaRepository<TotalView, Long>{

    TotalView findByUser(User user);
}