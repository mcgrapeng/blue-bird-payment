package com.zhangpeng.payment.core;

import com.zhangpeng.payment.center.PaymentProcessor;
import com.zhangpeng.payment.center.PaymentProcessorFactory;
import com.zhangpeng.payment.center.enums.PayWayEnum;
import com.zhangpeng.payment.core.utils.SpringUtils;
import org.springframework.stereotype.Component;

@Component("paymentProcessorFactory")
public final class AbstarctPaymentProcessorFactory  implements PaymentProcessorFactory {

    private PaymentProcessor paymentProcessor;


    public PaymentProcessor findPaymentProcessor(String payWayCode){
        if(PayWayEnum.MIAODAO.name().equals(payWayCode)){
            MDPaymentProcessor mdPaymentProcessor = SpringUtils.<MDPaymentProcessor>getBean("MDPaymentProcessor");
            paymentProcessor = mdPaymentProcessor;
        }else if(PayWayEnum.WEIXIN.name().equals(payWayCode)){
            WXPaymentProcessor wxPaymentProcessor = SpringUtils.<WXPaymentProcessor>getBean("WXPaymentProcessor");
            paymentProcessor = wxPaymentProcessor;
        }else if(PayWayEnum.ALIPAY.name().equals(payWayCode)){
            //
        }
        return paymentProcessor;
    }



}
