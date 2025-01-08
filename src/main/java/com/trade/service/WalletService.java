package com.trade.service;

import com.trade.model.Orders;
import com.trade.model.User;
import com.trade.model.Wallet;

public interface WalletService {

    Wallet getUserWallet(User user);

    Wallet addBalance(Wallet wallet,Long money);

    Wallet findWalletById(Long id) throws Exception;

    Wallet walletToWalletTransfer(User sender, Wallet receiverWallet,Long amount) throws Exception;

    Wallet payOrderPayment(Orders order, User user) throws Exception;
}
