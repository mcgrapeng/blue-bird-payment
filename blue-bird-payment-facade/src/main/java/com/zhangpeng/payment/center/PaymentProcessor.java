package com.zhangpeng.payment.center;


import java.util.Map;

public interface PaymentProcessor {

    /**
     * 支付
     *
     * @return
     */
    PaymentRES pay(PaymentREQ req);


    /**
     * 支付回调
     *
     * @param tradeNo         第三方支付机构流水号
     * @param tradeOrderNo    第三方支付机构订单号
     * @param trxNo           支付系统的支付流水号
     * @param merchantOrderNo 商户平台订单号
     * @param merchantNo      商户号
     * @param orderAmount
     * @param payStatus
     * @param payFinishTime
     * @param sign            支付机构签名
     * @param resultCode
     */
    PaymentRES completePay(String payProductNo, String payTypeCode, String tradeNo, String tradeOrderNo, String trxNo, String merchantOrderNo
            , String merchantNo, String orderAmount, String payStatus
            , String payFinishTime, String sign, String resultCode);

    PaymentRES completePay(String payWayCode, Map<String,String> notifyMap);


    /**
     * 支付结果
     *
     * @param payProductNo
     * @param payTypeCode
     * @param merchantNo
     * @param merchantOrderNo 第三方支付机构订单号
     * @return
     */
    PaymentRES completePayResult(String payProductNo, String payTypeCode, String merchantNo, String merchantOrderNo);

}
