package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
//	@Modifying
//	@Transactional
//    @Query(value = "DELETE FROM categories c WHERE c.category_id = :id", nativeQuery = true)
//    void deleteCategoryByCategoryId (@Param("id") String id);
	
    long deleteByCategoryId (String categoryId);
}