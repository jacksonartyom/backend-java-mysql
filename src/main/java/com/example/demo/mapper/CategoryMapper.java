package com.example.demo.mapper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.demo.dto.request.CategoryRequest;
import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.entity.Category;

@Component
public class CategoryMapper {

	public Category toEntity(CategoryRequest req) {
		String uuid = UUID.randomUUID().toString().replace("-", "");

		Category category = new Category();
		category.setCategoryId(uuid);
		category.setName(req.getName());
		category.setType(req.getType());
		category.setUserId(req.getUserId());
		category.setIsSystemDefault(false);
		category.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		return category;
	}

	public CategoryResponse toResponse(Category category) {
		return CategoryResponse.builder().categoryId(category.getCategoryId()).name(category.getName())
				.type(category.getType()).build();
	}
}