package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.request.WalletRequest;
import com.example.demo.dto.response.WalletResponse;
import com.example.demo.entity.Wallet;
import com.example.demo.mapper.WalletMapper;
import com.example.demo.repository.WalletRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletService {

	private final WalletRepository repo;
	private final WalletMapper mapper;

	public List<WalletResponse> getAllByUserId(String userId) {
		return repo.findByUserId(userId).stream().map(mapper::toResponse).toList();
	}

	public WalletResponse create(WalletRequest req) {
		Wallet wallet = mapper.toEntity(req);
		return mapper.toResponse(repo.save(wallet));
	}

	public WalletResponse update(String walletId, WalletRequest req) {

		Wallet existing = repo.findByWalletId(walletId).orElseThrow(() -> new RuntimeException("Wallet not found"));

		Wallet wallet = mapper.toEntityUpdate(existing, req);
		return mapper.toResponse(repo.save(wallet));
	}
	
	public void delete(String walletId) {
		long deleted =  repo.deleteByWalletId(walletId);
		
		if (deleted == 0) {
		    throw new RuntimeException("Wallet not found");
		}
	}
}
