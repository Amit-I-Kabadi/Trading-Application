package com.trade.service;

import com.trade.model.User;
import com.trade.model.Withdrawal;

import java.util.List;

public interface WithdrawalService {

    Withdrawal requestWithDrawal(Long amount, User user);
    Withdrawal proceedWithdrawal(Long withdrawalId ,boolean accept) throws Exception;
    List<Withdrawal> getUsersWithdrawalHistory(User user );
    List<Withdrawal> getAllWithdrawalRequest();
}
