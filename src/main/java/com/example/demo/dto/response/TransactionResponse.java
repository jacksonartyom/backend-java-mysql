package com.example.demo.dto.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
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

	@JsonProperty("transaction_date")
	private String transactionDate;
	private CategoryResponse category;
	private String userId;
	
}