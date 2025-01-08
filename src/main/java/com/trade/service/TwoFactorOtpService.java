package com.trade.service;

import com.trade.model.TwoFactorOTP;
import com.trade.model.User;

public interface TwoFactorOtpService {

    TwoFactorOTP createTwoFactorOtp(User user,String otp,String jwt);

    //find by user
    TwoFactorOTP findByUser(Long userId);

    //find by id
    TwoFactorOTP findById(String id);

    //verify two factor otp
    boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOTP,String otp);

    void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP);
}
