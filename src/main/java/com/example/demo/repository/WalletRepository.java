package com.example.demo.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

	@Query(value = "SELECT * FROM wallets w WHERE w.user_id = :userId", nativeQuery = true)
	List<Wallet> findByUserId(@Param("userId") String userId);

	Optional<Wallet> findByWalletId(String walletId);
	
	long deleteByWalletId(String walletId);
	
	@Query(value = "SELECT COALESCE(SUM(balance), 0) AS total_balance FROM wallets WHERE user_id = :userId", nativeQuery = true)
	BigDecimal sumBalanceByUserId(@Param("userId") String userId);

	@Modifying
	@Query(value = "UPDATE wallets SET balance = balance + :amount, updated_at = :updatedAt  WHERE wallet_id = :walletId", nativeQuery = true)
	void updateBalance(@Param("walletId") String walletId, @Param("amount") BigDecimal amount, @Param("updatedAt") LocalDateTime updatedAt);
}