package com.zhangpeng.payment.core;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.zhangpeng.payment.center.PayMDConfiguration;
import com.zhangpeng.payment.center.PaymentProcessor;
import com.zhangpeng.payment.center.PaymentREQ;
import com.zhangpeng.payment.center.PaymentRES;
import com.zhangpeng.payment.center.enums.MDPayConfigEnum;
import com.zhangpeng.payment.center.enums.TradeStatusEnum;
import com.zhangpeng.payment.core.utils.MDPaymentSign;
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
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml"})
public class PayProcessorTest {

    @Autowired
    private PaymentProcessor paymentProcessor;

    @Test public void pay(){

        PaymentREQ req = new PaymentREQ();
        req.setAppId(MDPayConfigEnum.MD_WX_PROGRAM_PAY.getAppId());
        //req.setAccessMode(MDPayConfigEnum.MD_WX_PROGRAM_PAY.getAccessMode());
        req.setMerchantNo(MDPayConfigEnum.MD_WX_PROGRAM_PAY.getMerchantNo());
        req.setProductNo(PayMDConfiguration.PRODUCT_NO);
        req.setPayType(MDPayConfigEnum.MD_WX_PROGRAM_PAY.getPayType());
        req.setMerchantName("卡卡得");
        req.setMerchantOrderNo("10010001000333");
        req.setOrderAmount(new BigDecimal("0.01"));
        req.setNotifyUrl("www.baidu.com");
        req.setOrderDate(new Date());
        req.setOrderIp("127.0.0.1");
        req.setOrderTime(new Date());
        req.setProductName("腾讯大王卡");
        req.setPaySuccessUrl("www.baidu.com");
        req.setOpenId("ogbn-1dwuwAdD8Bqh0pLo2abiO2g");

        PaymentRES paymentRES = paymentProcessor.pay(req);

        System.out.println(JSON.toJSONString(paymentRES));

    }


    @Test public void payNotify(){

        Map<String,String> param = Maps.newHashMap();
        param.put("tradeNo","100000000000000");
        param.put("outTradeNo","10010001000");
        param.put("payStatus", TradeStatusEnum.WAITING_PAYMENT.name());
        param.put("amount","0.01");
        try {
            String sign = MDPaymentSign.signRequest(param, MDPayConfigEnum.MD_WX_PROGRAM_PAY.getKey());
            paymentProcessor.completePay("","","","1231231","10010001000333","10010001000","100"
                    ,"0.01", TradeStatusEnum.WAITING_PAYMENT.name(),"2019-09-28 10:00:00"
                    ,sign,"fail");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
