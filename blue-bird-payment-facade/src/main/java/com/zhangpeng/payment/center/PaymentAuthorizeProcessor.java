package com.zhangpeng.payment.center;

public interface PaymentAuthorizeProcessor {

    PaymentRES payOpenId(PaymentREQ paymentREQ);

    PaymentRES paySign(PaymentREQ paymentREQ);

}
