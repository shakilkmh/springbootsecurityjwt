package com.mollah.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mollah.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    
	User findFirstByEmail(String email);
    
    Boolean existsByEmail(String email);
    
}
