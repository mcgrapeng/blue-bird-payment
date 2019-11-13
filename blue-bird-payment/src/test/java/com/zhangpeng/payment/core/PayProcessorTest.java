package com.zhangpeng.payment.core;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.zhangpeng.payment.center.*;
import com.zhangpeng.payment.center.enums.TradeStatusEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/spring-context.xml"})
public class PayProcessorTest {

    @Autowired
    private PaymentProcessor paymentProcessor;

    @Test public void pay(){

        PaymentREQ req = new PaymentREQ();
        req.setAppId(PayMDConfiguration.APP_ID);
        req.setAccessMode("MINIPROGRAM");
        req.setMerchantNo(PayMDConfiguration.MERCHANT_NO);
        req.setProductNo(PayMDConfiguration.PRODUCT_NO);
        req.setPayType("WX_PROGRAM_PAY");
        req.setMerchantName("卡卡得");
        req.setMerchantOrderNo("10010001000");
        req.setOrderAmount(new BigDecimal("0.01"));
        req.setNotifyUrl("www.baidu.com");
        req.setOrderDate(new Date());
        req.setOrderIp("127.0.0.1");
        req.setOrderTime(new Date());
        req.setPayType("WX_PROGRAM_PAY");
        req.setProductName("腾讯大王卡");
        req.setPaySuccessUrl("www.baidu.com");


        PaymentRES paymentRES = paymentProcessor.programPay(req);

        System.out.println(JSON.toJSONString(paymentRES));

    }


    @Test public void payNotify(){

        Map<String,String> param = Maps.newHashMap();
        param.put("tradeNo","100000000000000");
        param.put("outTradeNo","10010001000");
        param.put("payStatus", TradeStatusEnum.WAITING_PAYMENT.name());
        param.put("amount","0.01");
        try {
            String sign = PaymentSign.signRequest(param, PayMDConfiguration.KEY);
            paymentProcessor.completePayNotify("","1231231","100000000000000","10010001000",PayMDConfiguration.MERCHANT_NO
                    ,"0.01", TradeStatusEnum.WAITING_PAYMENT.name(),"2019-09-28 10:00:00"
                    ,sign,"fail");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}