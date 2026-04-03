package com.example.demo.dto.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransactionRequest {
	private String walletId;
	private String name;
	private BigDecimal amount;
	private String note;
	private String type;
	private String transactionDate;
	private String categoryId;
	private String userId;
}