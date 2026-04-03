package com.example.demo.mapper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.demo.dto.request.TransactionRequest;
import com.example.demo.dto.response.TransactionResponse;
import com.example.demo.entity.Transaction;

@Component
public class TransactionMapper {

	public Transaction toEntity(TransactionRequest req) {
		String uuid = UUID.randomUUID().toString().replace("-", "");

		Transaction transaction = new Transaction();
		transaction.setTransactionId(uuid);
		transaction.setName(req.getName());
		transaction.setNote(req.getNote());
		transaction.setAmount(req.getAmount());
		transaction.setType(req.getType());
		transaction.setTransactionDate(req.getTransactionDate());
		transaction.setWalletId(req.getWalletId());
		transaction.setCategoryId(req.getCategoryId());
		transaction.setUserId(req.getUserId());
		transaction.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		return transaction;
	}

	public Transaction toEntityUpdate(Transaction oldTransaction, TransactionRequest req) {
		Transaction transaction = new Transaction();
		transaction.setId(oldTransaction.getId());
		transaction.setTransactionId(oldTransaction.getTransactionId());
		transaction.setName(req.getName());
		transaction.setNote(req.getNote());
		transaction.setAmount(oldTransaction.getAmount());
		transaction.setType(oldTransaction.getType());
		transaction.setTransactionDate(oldTransaction.getTransactionDate());
		transaction.setWalletId(oldTransaction.getWalletId());
		transaction.setCategoryId(req.getCategoryId());
		transaction.setUserId(oldTransaction.getUserId());
		transaction.setCreatedAt(oldTransaction.getCreatedAt());
		transaction.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		return transaction;
	}

	public TransactionResponse toResponse(Transaction transaction) {
		return TransactionResponse.builder().transactionId(transaction.getTransactionId()).name(transaction.getName())
				.note(transaction.getNote()).amount(transaction.getAmount()).type(transaction.getType())
				.transactionDate(transaction.getTransactionDate()).walletId(transaction.getWalletId())
				.categoryId(transaction.getCategoryId()).userId(transaction.getUserId()).build();
	}
}