package com.example.demo.dto.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletResponse {
	@JsonProperty("_id")
	private String walletId;

	@JsonProperty("wallet_name")
	private String walletName;

	@JsonProperty("wallet_detail")
	private String walletDetail;

	@JsonSerialize(using = ToStringSerializer.class)
	private BigDecimal balance;
	private String userId;
}