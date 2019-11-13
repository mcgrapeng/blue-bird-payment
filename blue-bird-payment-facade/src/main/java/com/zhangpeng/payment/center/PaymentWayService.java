package com.zhangpeng.payment.center;

import com.zhangpeng.payment.center.domain.PaymentWay;

/**
 * 支付方式管理
 * 张朋
 */
public interface PaymentWayService {

    /**
     * 根据支付方式、渠道编码获取数据
     * @param payProductCode
     * @param payWayCode
     * @param payTypeCode
     * @return
     */
    PaymentWay getPaymentWay(String payProductCode, String payWayCode, String payTypeCode);
}
