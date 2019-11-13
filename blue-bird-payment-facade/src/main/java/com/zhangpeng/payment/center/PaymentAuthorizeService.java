package com.zhangpeng.payment.center;

import com.zhangpeng.payment.center.domain.PaymentAuthorize;

public interface PaymentAuthorizeService {

    String  getOpenId(String userNo);

    void  createOpenId(PaymentAuthorize paymentAuthorize);

    void deleteOpenId(String userNo);
}
