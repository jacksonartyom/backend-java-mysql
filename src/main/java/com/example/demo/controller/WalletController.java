package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.WalletRequest;
import com.example.demo.dto.response.WalletResponse;
import com.example.demo.exception.SuccessResponse;
import com.example.demo.service.WalletService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/wallet")
@RequiredArgsConstructor
public class WalletController {

	private final WalletService service;

	@GetMapping
	public SuccessResponse<List<WalletResponse>> getAllWallet(Authentication authentication) {
		String userId = (String) authentication.getPrincipal();
		SuccessResponse<List<WalletResponse>> res = new SuccessResponse<>("success", service.getAllByUserId(userId));
		return res;
	}

	@PostMapping
	public SuccessResponse<WalletResponse> createWallet(@RequestBody WalletRequest req, Authentication authentication) {
		String userId = (String) authentication.getPrincipal();
		req.setUserId(userId);
		SuccessResponse<WalletResponse> res = new SuccessResponse<>("success", service.create(req));
		return res;
	}

	@PutMapping("/{walletId}")
	public SuccessResponse<WalletResponse> updateWallet(@PathVariable String walletId, @RequestBody WalletRequest req,
			Authentication authentication) {
		String userId = (String) authentication.getPrincipal();
		req.setUserId(userId);
		SuccessResponse<WalletResponse> res = new SuccessResponse<>("success", service.update(walletId, req));
		return res;
	}

	@DeleteMapping("/{walletId}")
	public SuccessResponse<String> deleteByWalletId(@PathVariable String walletId) {
		service.delete(walletId);
		SuccessResponse<String> res = new SuccessResponse<>("success", "delete success");
		return res;
	}
}
