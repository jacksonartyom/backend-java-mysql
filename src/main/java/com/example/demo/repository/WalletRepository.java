package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

	@Query(value = "SELECT * FROM wallets w WHERE w.user_id = :userId", nativeQuery = true)
	List<Wallet> findByUserId(@Param("userId") String userId);

	Optional<Wallet> findByWalletId(String walletId);
	
	long deleteByWalletId(String walletId);
}