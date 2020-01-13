package com.zhangpeng.payment.core;

import com.zhangpeng.payment.center.PaymentREQ;
import com.zhangpeng.payment.center.PaymentRES;
import com.zhangpeng.payment.center.domain.PaymentWay;
import com.zhangpeng.payment.center.enums.PayWayEnum;
import com.zhangpeng.payment.center.ex.PaymentBizException;
import com.zhangpeng.payment.core.enums.WeiXinTradeTypeEnum;
import com.zhangpeng.payment.core.utils.WXPaymentUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service("WXPaymentProcessor")
public class WXPaymentProcessor extends  PaymentProcessorTemplate  {

    @Override
    public PaymentRES process(PaymentWay paymentWay, PaymentREQ paymentREQ) {
        if (PayWayEnum.WEIXIN.name().equals(paymentWay.getPayWayCode())) {
            Map<String, Object> ret = WXPaymentUtils.unifiedOrder(WeiXinTradeTypeEnum.JSAPI, paymentREQ.getTrxNo(),paymentREQ.getMerchantOrderNo(), "联通5G冰淇淋套餐"
                    , paymentREQ.getOrderAmount(), paymentREQ.getOrderIp(), paymentREQ.getNotifyUrl(), paymentREQ.getOpenId(), null);
            return PaymentRES.of(PaymentBizException.SUCCESS,ret,"支付成功!");
        }
        return PaymentRES.of(PaymentBizException.FAILED,"支付失败!");
    }
}

