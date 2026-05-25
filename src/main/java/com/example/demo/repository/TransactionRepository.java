package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.dto.response.TransactionDashboardResponse;
import com.example.demo.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query(value = "SELECT "
			+ " tran.transaction_id, tran.wallet_id,tran.name,tran.amount,"
			+ " tran.note, tran.type, tran.transaction_date, tran.category_id,"
			+ " cate.name, tran.user_id "
			+ "FROM "
			+ "    transactions tran "
			+ "        LEFT JOIN "
			+ "    categories cate ON tran.category_id = cate.category_id "
			+ "WHERE "
			+ "    tran.wallet_id = :walletId AND tran.transaction_date >= :dateFrom "
			+ "  AND tran.transaction_date < :dateTo ORDER BY tran.transaction_date DESC", nativeQuery = true)
	List<TransactionDashboardResponse> findByWalletId(@Param("walletId") String walletId, @Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);

	Optional<Transaction> findByTransactionId(String transactionId);
	
	long deleteByTransactionId(String transactionId);
	
	List<Transaction> findTop5ByUserIdOrderByTransactionDateDesc(String userId);
	
	
	@Query(value = "SELECT "
			+ " tran.transaction_id, tran.wallet_id,tran.name,tran.amount,"
			+ " tran.note, tran.type, tran.transaction_date, tran.category_id,"
			+ " cate.name, tran.user_id "
			+ "FROM "
			+ "    transactions tran "
			+ "        LEFT JOIN "
			+ "    categories cate ON tran.category_id = cate.category_id "
			+ "WHERE "
			+ "    tran.user_id = :userId ORDER BY tran.transaction_date DESC LIMIT 5", nativeQuery = true)
	List<TransactionDashboardResponse> findByUserId(@Param("userId") String userId);
}