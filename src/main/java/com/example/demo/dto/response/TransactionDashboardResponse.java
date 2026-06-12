package com.example.demo.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TransactionDashboardResponse {
    private String transactionId;
	private String walletId;
	private String name;
	private BigDecimal amount;
	private String note;
	private String type;
	private LocalDateTime transactionDate;
	private String categoryId;
	private String categoryName;
	private String userId;
	
}