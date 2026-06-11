package com.example.demo.repository;

import com.example.demo.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * FROM categories c WHERE c.user_id = :userId OR c.is_system_default = 1", nativeQuery = true)
    List<Category> findByUserIdAndDefault(@Param("userId") String userId);
	
    long deleteByCategoryId (String categoryId);
}