package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.request.TransactionRequest;
import com.example.demo.dto.response.TransactionResponse;
import com.example.demo.entity.Transaction;
import com.example.demo.mapper.TransactionMapper;
import com.example.demo.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {

	private final TransactionRepository repo;
	private final TransactionMapper mapper;

	public List<TransactionResponse> getAllByWalletId(String walletId) {
		return repo.findByWalletId(walletId).stream().map(mapper::toResponse).toList();
	}

	public String create(String userId, List<TransactionRequest> req) {

		List<Transaction> transactionList = new ArrayList<>();
		for (TransactionRequest item : req) {
			Transaction transaction = mapper.toEntity(item);
			transaction.setUserId(userId);
			transactionList.add(transaction);
		}

		try {
			repo.saveAll(transactionList);
			return "Transaction save success";
		} catch (Exception e) {
			throw new RuntimeException("Transaction can't save");
		}
	}

	public TransactionResponse update(String transactionId, TransactionRequest req) {

		Transaction existing = repo.findByTransactionId(transactionId)
				.orElseThrow(() -> new RuntimeException("Transaction not found"));

		Transaction transaction = mapper.toEntityUpdate(existing, req);
		return mapper.toResponse(repo.save(transaction));
	}

	public void delete(String transactionId) {
		long deleted = repo.deleteByTransactionId(transactionId);

		if (deleted == 0) {
			throw new RuntimeException("Transaction not found");
		}
	}
}
