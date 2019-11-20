package com.zhangpeng.payment.core;

import com.zhangpeng.payment.center.PaymentProcessor;
import com.zhangpeng.payment.center.enums.PayWayEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("paymentProcessorFactory")
public  class AbstarctPaymentProcessorFactory  implements PaymentProcessorFactory  {

    @Autowired
    private MDPaymentProcessor mdPaymentProcessor;

   // @Autowired
  //  private WXPaymentProcessor wxPaymentProcessor;


    public PaymentProcessor findPaymentProcessor(String payWayCode){
        if(PayWayEnum.MIAODAO.name().equals(payWayCode)){
           return mdPaymentProcessor;
        }else if(PayWayEnum.WEIXIN.name().equals(payWayCode)){
         //  return wxPaymentProcessor;
        }else if(PayWayEnum.ALIPAY.name().equals(payWayCode)){
            //
        }
        return null;
    }
}
