package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	List<Transaction> findByWalletId(String walletId);

	Optional<Transaction> findByTransactionId(String transactionId);
	
	long deleteByTransactionId(String transactionId);
}