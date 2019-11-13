package com.zhangpeng.payment.core;

import com.alibaba.fastjson.JSON;
import com.zhangpeng.payment.center.PaymentTokenService;
import com.zhangpeng.payment.center.domain.PaymentToken;
import com.zhangpeng.payment.center.enums.TradeStatusEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml"})
public class PayServiceTest {

    @Autowired
    private PaymentTokenService paymentTokenService;


    @Test public void createPaymentToken(){
        PaymentToken token = new PaymentToken();
        token.setStatus(TradeStatusEnum.SUCCESS.name());

        token.setCreater("张朋");
        token.setEditor("张朋");
        token.setEditTime(new Date());
        token.setRemark("备注");


        token.setTrxNo("100000000000000").setTrxType("消费").setFeeRate(BigDecimal.ZERO)
        .setMerchantName("卡卡得")
        .setMerchantNo("35")
        .setNotifyUrl("www.baidu.com")
        .setMerchantOrderNo("10010001000")
        .setOrderAmount(new BigDecimal("100.09"))
                .setOrderFrom("用户消费")
                .setOrderIp("127.0.0.1")
                .setOrderRefererUrl("www.baidu.com")
                .setPaySuccessTime(new Date())
                .setPayTypeCode("10")
                .setPayTypeName("微信小程序支付")
                .setPayWayName("微信")
                .setPayWayCode("01")
                .setProductName("腾讯大王卡")
                .setReturnUrl("www.baidu.com")
                .setCompleteTime(new Date())
                .setRefund(Boolean.FALSE);

        paymentTokenService.createPaymentToken(token);

    }


    @Test public void updatePaymentToken(){

        PaymentToken token = new  PaymentToken();
        token.setStatus(TradeStatusEnum.SUCCESS.name());
        token.setId(1);
        token.setCreater("张朋");
        token.setEditor("张朋");
        token.setEditTime(new Date());
        token.setRemark("备注");


        token.setTrxNo("100000000000000").setTrxType("消费").setFeeRate(BigDecimal.ZERO)
                .setMerchantName("卡卡得22222")
                .setMerchantNo("35")
                .setNotifyUrl("www.baidu.com")
                .setMerchantOrderNo("10010001000")
                .setOrderAmount(new BigDecimal("100.09"))
                .setOrderFrom("用户消费")
                .setOrderIp("127.0.0.1")
                .setOrderRefererUrl("www.baidu.com")
                .setPaySuccessTime(new Date())
                .setPayTypeCode("10")
                .setPayTypeName("微信小程序支付")
                .setPayWayName("微信")
                .setPayWayCode("01")
                .setProductName("腾讯大王卡")
                .setReturnUrl("www.baidu.com")
                .setCompleteTime(new Date())
                .setRefund(Boolean.FALSE);

        paymentTokenService.updatePaymentToken(token);

    }




    @Test public void updatePaymentTokenStatus(){
        paymentTokenService.updatePaymentTokenStatus("100000000000000",TradeStatusEnum.FAILED.name());
    }


    @Test public void queryPaymentTokenByTradeNo(){
        PaymentToken paymentToken = paymentTokenService.queryPaymentTokenByTrxNo("100000000000000");
        System.out.println(JSON.toJSONString(paymentToken));
    }
}
