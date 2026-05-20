package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.response.DashboardResponse;
import com.example.demo.exception.SuccessResponse;
import com.example.demo.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

	private final DashboardService service;

	@GetMapping
	public SuccessResponse<DashboardResponse> getAllCategory(Authentication authentication) {
		String userId = (String) authentication.getPrincipal();
		SuccessResponse<DashboardResponse> res = new SuccessResponse<>("success",
				service.getDashboardDetail(userId));
		return res;
	}
}