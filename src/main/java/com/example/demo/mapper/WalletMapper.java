package com.example.demo.mapper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.demo.dto.request.WalletRequest;
import com.example.demo.dto.response.WalletResponse;
import com.example.demo.entity.Wallet;

@Component
public class WalletMapper {

	public Wallet toEntity(WalletRequest req) {
		String uuid = UUID.randomUUID().toString().replace("-", "");

		Wallet wallet = new Wallet();
		wallet.setWalletId(uuid);
		wallet.setWalletName(req.getWalletName());
		wallet.setWalletDetail(req.getWalletDetail());
		wallet.setBalance(req.getBalance());
		wallet.setUserId(req.getUserId());
		wallet.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		return wallet;
	}

	public Wallet toEntityUpdate(Wallet oldWallet, WalletRequest req) {
		Wallet wallet = new Wallet();
		wallet.setId(oldWallet.getId());
		wallet.setWalletId(oldWallet.getWalletId());
		wallet.setWalletName(req.getWalletName());
		wallet.setWalletDetail(req.getWalletDetail());
		wallet.setBalance(oldWallet.getBalance());
		wallet.setUserId(oldWallet.getUserId());
		wallet.setCreatedAt(oldWallet.getCreatedAt());
		wallet.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		return wallet;
	}

	public WalletResponse toResponse(Wallet wallet) {
		return WalletResponse.builder().walletId(wallet.getWalletId()).walletName(wallet.getWalletName())
				.walletDetail(wallet.getWalletDetail()).balance(wallet.getBalance()).userId(wallet.getUserId()).build();
	}
}