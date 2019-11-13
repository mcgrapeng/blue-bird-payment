package com.zhangpeng.payment.center;

import com.zhangpeng.payment.center.domain.PaymentToken;

public interface PaymentTokenService {

    /**
     * 创建支付明细
     * @param paymentToken
     * @return
     */
    void createPaymentToken(PaymentToken paymentToken);


    /**
     * 更新支付明细状态
     * @param paymentToken
     */
    void updatePaymentToken(PaymentToken paymentToken);


    /**
     * 根据支付系统支付流水号
     * 更新订单状态
     * @param
     */
    void updatePaymentTokenStatus(String trxNo, String payStatus);


    /**
     * 查询交易记录
     * @param merchantNo
     * @param merchantOrderNo
     * @return
     */
    PaymentToken  queryPaymentToken(String merchantNo, String merchantOrderNo);


    /**
     * 根据支付系统交易流水号获取支付流水
     * @param trxNo
     * @return
     */
    PaymentToken  queryPaymentTokenByTrxNo(String trxNo);


    /**
     * 根据第三方支付机构订单号获取支付明细
     * @param tradeOrderNo
     * @return
     */
    PaymentToken  queryPaymentTokenByTradeOrderNo(String tradeOrderNo);
}
