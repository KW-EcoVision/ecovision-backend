package com.ecovision.ecovision.repository;

import com.ecovision.ecovision.entity.Plogging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//ploggingRepository
public interface PloggingRepository extends JpaRepository<Plogging, Long>{

}
