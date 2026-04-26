package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.request.TransactionRequest;
import com.example.demo.dto.response.TransactionResponse;
import com.example.demo.entity.Transaction;
import com.example.demo.mapper.TransactionMapper;
import com.example.demo.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository repo;

    @Mock
    private TransactionMapper mapper;

    @InjectMocks
    private TransactionService service;

    // ======================
    // ✅ getAllByWalletId
    // ======================
    @Test
    void shouldReturnTransactionsByWalletId() {
        String walletId = "wallet-1";

        Transaction t1 = new Transaction();
        Transaction t2 = new Transaction();

        TransactionResponse r1 = new TransactionResponse(walletId, walletId, walletId, null, walletId, walletId, walletId, walletId, walletId);
        TransactionResponse r2 = new TransactionResponse(walletId, walletId, walletId, null, walletId, walletId, walletId, walletId, walletId);

        when(repo.findByWalletId(walletId)).thenReturn(List.of(t1, t2));
        when(mapper.toResponse(t1)).thenReturn(r1);
        when(mapper.toResponse(t2)).thenReturn(r2);

        List<TransactionResponse> result = service.getAllByWalletId(walletId);

        assertEquals(2, result.size());
        assertEquals(r1, result.get(0));
        assertEquals(r2, result.get(1));

        verify(repo).findByWalletId(walletId);
        verify(mapper, times(2)).toResponse(any(Transaction.class));
    }

    // ======================
    // ✅ create SUCCESS
    // ======================
    @Test
    void shouldCreateTransactionsSuccessfully() {
        String userId = "user-1";

        TransactionRequest req1 = new TransactionRequest();
        TransactionRequest req2 = new TransactionRequest();

        Transaction t1 = new Transaction();
        Transaction t2 = new Transaction();

        when(mapper.toEntity(any(TransactionRequest.class)))
                .thenReturn(t1, t2);

        String result = service.create(userId, List.of(req1, req2));

        assertEquals("Transaction save success", result);

        // ตอนนี้จะผ่านแล้ว ✅
        assertEquals(userId, t1.getUserId());
        assertEquals(userId, t2.getUserId());

        verify(repo).saveAll(anyList());
    }

    // ======================
    // ❌ create ERROR
    // ======================
    @Test
    void shouldThrowExceptionWhenSaveFail() {
        String userId = "user-1";
        TransactionRequest req = new TransactionRequest();
        Transaction t = new Transaction();

        when(mapper.toEntity(req)).thenReturn(t);
        doThrow(new RuntimeException()).when(repo).saveAll(anyList());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.create(userId, List.of(req));
        });

        assertEquals("Transaction can't save", ex.getMessage());
    }

    // ======================
    // ✅ update SUCCESS
    // ======================
    @Test
    void shouldUpdateTransactionSuccessfully() {
        String transactionId = "tx-1";

        Transaction existing = new Transaction();
        TransactionRequest req = new TransactionRequest();
        Transaction updated = new Transaction();
        Transaction saved = new Transaction();
        TransactionResponse response = new TransactionResponse(transactionId, transactionId, transactionId, null, transactionId, transactionId, transactionId, transactionId, transactionId);

        when(repo.findByTransactionId(transactionId)).thenReturn(Optional.of(existing));
        when(mapper.toEntityUpdate(existing, req)).thenReturn(updated);
        when(repo.save(updated)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        TransactionResponse result = service.update(transactionId, req);

        assertNotNull(result);
        assertEquals(response, result);

        verify(repo).findByTransactionId(transactionId);
        verify(repo).save(updated);
    }

    // ======================
    // ❌ update NOT FOUND
    // ======================
    @Test
    void shouldThrowExceptionWhenTransactionNotFound() {
        String transactionId = "tx-999";

        when(repo.findByTransactionId(transactionId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.update(transactionId, new TransactionRequest());
        });

        assertEquals("Transaction not found", ex.getMessage());
    }

    // ======================
    // ✅ delete SUCCESS
    // ======================
    @Test
    void shouldDeleteTransactionSuccessfully() {
        String transactionId = "tx-1";

        when(repo.deleteByTransactionId(transactionId)).thenReturn(1L);

        service.delete(transactionId);

        verify(repo).deleteByTransactionId(transactionId);
    }

    // ======================
    // ❌ delete NOT FOUND
    // ======================
    @Test
    void shouldThrowExceptionWhenDeleteNotFound() {
        String transactionId = "tx-999";

        when(repo.deleteByTransactionId(transactionId)).thenReturn(0L);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.delete(transactionId);
        });

        assertEquals("Transaction not found", ex.getMessage());
    }
}