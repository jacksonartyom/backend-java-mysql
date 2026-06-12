package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String transactionId;
	private String walletId;
	private String name;
	private BigDecimal amount;
	private String note;
	private String type;
	private LocalDateTime transactionDate;
	private String categoryId;
	private String userId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
