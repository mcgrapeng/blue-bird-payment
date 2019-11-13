package com.zhangpeng.payment.center;

import com.zhangpeng.payment.center.domain.PaymentOrder;

public interface PaymentOrderService {

    /**
     * 根据商户编号及商户订单号获取支付订单信息
     * @param merchantNo
     * @param merchantOrderNo
     * @return
     */
    PaymentOrder selectByMerchantNoAndMerchantOrderNo(String merchantNo, String merchantOrderNo);


    /**
     * 创建支付订单
     * @param paymentOrder
     */
    void createPaymentOrder(PaymentOrder paymentOrder);



    /**
     * 更新支付订单
     * @param paymentOrder
     */
    void updatePaymentOrder(PaymentOrder paymentOrder);


    /**
     * 更新订单状态
     */
    void updatePaymentOrderStatus(String merchantNo, String merchantOrderNo, String orderStatus);
}
