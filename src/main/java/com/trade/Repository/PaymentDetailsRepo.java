package com.trade.Repository;

import com.trade.model.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailsRepo extends JpaRepository<PaymentDetails,Long> {

    PaymentDetails findByUserId(Long userId);
}
