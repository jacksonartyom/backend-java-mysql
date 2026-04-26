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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.TransactionRequest;
import com.example.demo.dto.response.TransactionResponse;
import com.example.demo.exception.SuccessResponse;
import com.example.demo.service.TransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

	private final TransactionService service;

	@GetMapping
	public SuccessResponse<List<TransactionResponse>> getAllTransactionByWalletId(@RequestParam String walletId, Authentication authentication) {
		SuccessResponse<List<TransactionResponse>> res = new SuccessResponse<>("success",
				service.getAllByWalletId(walletId));
		return res;
	}

	@PostMapping
	public SuccessResponse<String> createTransaction(@RequestBody List<TransactionRequest> req,
			Authentication authentication) {
		String userId = (String) authentication.getPrincipal();
		SuccessResponse<String> res = new SuccessResponse<>("success", service.create(userId, req));
		return res;
	}

	@PutMapping("/{transactionId}")
	public SuccessResponse<TransactionResponse> updateTransaction(@PathVariable String transactionId,
			@RequestBody TransactionRequest req, Authentication authentication) {
		String userId = (String) authentication.getPrincipal();
		req.setUserId(userId);
		SuccessResponse<TransactionResponse> res = new SuccessResponse<>("success", service.update(transactionId, req));
		return res;
	}

	@DeleteMapping("/{transactionId}")
	public SuccessResponse<String> deleteByTransactionId(@PathVariable String transactionId) {
		service.delete(transactionId);
		SuccessResponse<String> res = new SuccessResponse<>("success", "delete success");
		return res;
	}
}
