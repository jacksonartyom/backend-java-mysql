package com.example.demo.dto.response;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {
	
	 @JsonProperty("total_balance")
	private BigDecimal totalBalance;
	private List<WalletResponse> wallets;
	 @JsonProperty("recent_transactions")
	private List<TransactionDashboardResponse> recentTransactions;
}