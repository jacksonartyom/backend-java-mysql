package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.request.CategoryRequest;
import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.entity.Category;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

	private final CategoryRepository repo;
	private final CategoryMapper mapper;

	public List<CategoryResponse> findByUserIdAndDefault(String userId) {
		return repo.findByUserIdAndDefault(userId).stream().map(mapper::toResponse).toList();
	}

	public CategoryResponse create(CategoryRequest req) {
		Category category = mapper.toEntity(req);
		return mapper.toResponse(repo.save(category));
	}
	
	public void delete(String categoryId) {
		long deleted =  repo.deleteByCategoryId(categoryId);
		
		if (deleted == 0) {
		    throw new RuntimeException("Category not found");
		}
	}
}