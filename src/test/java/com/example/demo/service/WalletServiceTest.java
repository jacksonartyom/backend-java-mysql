package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.request.WalletRequest;
import com.example.demo.dto.response.WalletResponse;
import com.example.demo.entity.Wallet;
import com.example.demo.mapper.WalletMapper;
import com.example.demo.repository.WalletRepository;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository repo;

    @Mock
    private WalletMapper mapper;

    @InjectMocks
    private WalletService service;

    // ======================
    // ✅ getAllByUserId
    // ======================
    @Test
    void shouldReturnWalletsByUserId() {
        String userId = "user-1";

        Wallet w1 = new Wallet();
        Wallet w2 = new Wallet();

        WalletResponse r1 = new WalletResponse();
        WalletResponse r2 = new WalletResponse();

        when(repo.findByUserId(userId)).thenReturn(List.of(w1, w2));
        when(mapper.toResponse(w1)).thenReturn(r1);
        when(mapper.toResponse(w2)).thenReturn(r2);

        List<WalletResponse> result = service.getAllByUserId(userId);

        assertEquals(2, result.size());
        assertEquals(r1, result.get(0));
        assertEquals(r2, result.get(1));

        verify(repo).findByUserId(userId);
        verify(mapper, times(2)).toResponse(any(Wallet.class));
    }

    // ======================
    // ✅ create
    // ======================
    @Test
    void shouldCreateWalletSuccessfully() {
        WalletRequest req = new WalletRequest();

        Wallet entity = new Wallet();
        Wallet saved = new Wallet();
        WalletResponse response = new WalletResponse();

        when(mapper.toEntity(req)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        WalletResponse result = service.create(req);

        assertNotNull(result);
        assertEquals(response, result);

        verify(mapper).toEntity(req);
        verify(repo).save(entity);
        verify(mapper).toResponse(saved);
    }

    // ======================
    // ✅ update SUCCESS
    // ======================
    @Test
    void shouldUpdateWalletSuccessfully() {
        String walletId = "wallet-1";

        Wallet existing = new Wallet();
        WalletRequest req = new WalletRequest();
        Wallet updated = new Wallet();
        Wallet saved = new Wallet();
        WalletResponse response = new WalletResponse();

        when(repo.findByWalletId(walletId)).thenReturn(Optional.of(existing));
        when(mapper.toEntityUpdate(existing, req)).thenReturn(updated);
        when(repo.save(updated)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        WalletResponse result = service.update(walletId, req);

        assertNotNull(result);
        assertEquals(response, result);

        verify(repo).findByWalletId(walletId);
        verify(repo).save(updated);
    }

    // ======================
    // ❌ update NOT FOUND
    // ======================
    @Test
    void shouldThrowExceptionWhenWalletNotFound() {
        String walletId = "wallet-999";

        when(repo.findByWalletId(walletId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.update(walletId, new WalletRequest());
        });

        assertEquals("Wallet not found", ex.getMessage());
    }

    // ======================
    // ✅ delete SUCCESS
    // ======================
    @Test
    void shouldDeleteWalletSuccessfully() {
        String walletId = "wallet-1";

        when(repo.deleteByWalletId(walletId)).thenReturn(1L);

        service.delete(walletId);

        verify(repo).deleteByWalletId(walletId);
    }

    // ======================
    // ❌ delete NOT FOUND
    // ======================
    @Test
    void shouldThrowExceptionWhenDeleteWalletNotFound() {
        String walletId = "wallet-999";

        when(repo.deleteByWalletId(walletId)).thenReturn(0L);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.delete(walletId);
        });

        assertEquals("Wallet not found", ex.getMessage());
    }
}