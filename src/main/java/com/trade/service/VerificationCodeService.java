package com.trade.service;

import com.trade.domain.VerificationType;
import com.trade.model.User;
import com.trade.model.VerificationCode;
import org.springframework.stereotype.Service;


public interface VerificationCodeService {

    VerificationCode sendVerificationCode(User user, VerificationType verificationType);

    VerificationCode getVerificationCodeById(Long id) throws Exception;

    VerificationCode getVerificationCodeByUser(Long userId);

    void deleteVerificationCodeById(VerificationCode verificationCode);








}
