package com.example.demo.dto.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class WalletRequest {
	private String userId;
	private String walletId;
	private String walletName;
	private String walletDetail;
	private BigDecimal balance;
}
