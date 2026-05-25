package com.example.demo.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.response.TransactionDashboardResponse;
import com.example.demo.entity.Wallet;
import com.example.demo.repository.WalletRepository;
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
	private final WalletRepository walletRepo;

	public List<TransactionResponse> getAllByWalletId(String walletId, String month, String year) {
		String conditionDate = year + "-" + String.format("%02d", Integer.parseInt(month));
		String startDate = conditionDate + "-01";

		LocalDate dateFrom = LocalDate.parse(startDate);
		LocalDate dateTo = dateFrom.plusMonths(1);

		return repo.findByWalletId(walletId, dateFrom, dateTo).stream().map(mapper::toTransactionResponse).toList();
	}

	public String create(String userId, List<TransactionRequest> req) {

		String walletId = null;
		BigDecimal newBalance = BigDecimal.ZERO;

		List<Transaction> transactionList = new ArrayList<>();
		for (TransactionRequest item : req) {
			Transaction transaction = mapper.toEntity(item);
			transaction.setUserId(userId);
			transactionList.add(transaction);

			walletId = transaction.getWalletId();

			BigDecimal amount = transaction.getAmount();
			if("income".equals(transaction.getType())){
				newBalance = newBalance.add(amount);
			} else {
				newBalance = newBalance.subtract(amount);
			}
		}



		try {
			repo.saveAll(transactionList);

			if(walletId != null){
				walletRepo.updateBalance(walletId, newBalance, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
			}

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
