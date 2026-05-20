package com.example.demo.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TransactionResponse {
    private String transactionId;
	private String walletId;
	private String name;
	private BigDecimal amount;
	private String note;
	private String type;
	private String transactionDate;
	private String categoryId;
	private String categoryName;
	private String userId;
	
}