package com.trade.service;

import com.trade.model.PaymentDetails;
import com.trade.model.User;

public interface PaymentDetailsService {
    public PaymentDetails addPaymentDetails(String accountNumber,
                                            String accountHolderName,
                                            String ifsc,
                                            String bankName,
                                            User user);
    public PaymentDetails getUserPaymentDetails(User user);
}
