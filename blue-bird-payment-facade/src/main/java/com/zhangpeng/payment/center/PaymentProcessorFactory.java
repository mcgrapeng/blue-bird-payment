package com.zhangpeng.payment.center;

public interface PaymentProcessorFactory {
    PaymentProcessor findPaymentProcessor(String payWayCode);
}
