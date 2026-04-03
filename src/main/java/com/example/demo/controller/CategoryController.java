package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.CategoryRequest;
import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.exception.SuccessResponse;
import com.example.demo.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService service;

	@GetMapping
	public SuccessResponse<List<CategoryResponse>> getAllCategory(Authentication authentication) {
		SuccessResponse<List<CategoryResponse>> res = new SuccessResponse<>("success", service.getAll());
		return res;
	}

	@PostMapping
	public SuccessResponse<CategoryResponse> createCategory(@RequestBody CategoryRequest req,
			Authentication authentication) {
		String userId = (String) authentication.getPrincipal();
		req.setUserId(userId);
		SuccessResponse<CategoryResponse> res = new SuccessResponse<>("success", service.create(req));
		return res;
	}

	@DeleteMapping("/{categoryId}")
	public SuccessResponse<String> deleteByCategoryId(@PathVariable String categoryId) {
		service.delete(categoryId);
		SuccessResponse<String> res = new SuccessResponse<>("success", "delete success");
		return res;
	}
}