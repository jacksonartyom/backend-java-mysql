package com.example.demo.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.response.DashboardResponse;
import com.example.demo.dto.response.TransactionDashboardResponse;
import com.example.demo.dto.response.WalletResponse;
import com.example.demo.mapper.WalletMapper;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.WalletRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DashboardService {

	private final WalletRepository walletRepo;
	private final TransactionRepository transactionRepo;
	private final WalletMapper walletMapper;

	public DashboardResponse getDashboardDetail(String userId) {

		BigDecimal totalBalance = walletRepo.sumBalanceByUserId(userId);
		List<TransactionDashboardResponse> recentTransactions = transactionRepo.findByUserId(userId);
		List<WalletResponse> wallets = walletRepo.findByUserId(userId).stream().map(walletMapper::toResponse).toList();
		return DashboardResponse.builder().totalBalance(totalBalance).recentTransactions(recentTransactions)
				.wallets(wallets).build();
	}

}