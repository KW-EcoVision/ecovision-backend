package com.ecovision.ecovision.repository;

import com.ecovision.ecovision.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByUsername(String username);

    User findByUsername(String username);


    void deleteByUsername(String username);

}
