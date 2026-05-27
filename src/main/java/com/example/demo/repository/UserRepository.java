package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
	
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users u WHERE u.user_id = :userId", nativeQuery = true)
    User findByUserId(@Param("userId") String userId);
}