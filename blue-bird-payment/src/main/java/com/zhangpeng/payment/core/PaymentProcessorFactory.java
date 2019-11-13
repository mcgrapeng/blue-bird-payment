package com.zhangpeng.payment.core;

import com.zhangpeng.payment.center.PaymentProcessor;

public interface PaymentProcessorFactory {

    PaymentProcessor findPaymentProcessor(String payWayCode);
}
