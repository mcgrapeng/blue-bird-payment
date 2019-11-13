package com.zhangpeng.payment.core;

import com.zhangpeng.payment.center.PaymentAuthorizeService;
import com.zhangpeng.payment.center.domain.PaymentAuthorize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml"})
public class PayAuthorizeServiceTest {

    @Autowired
    private PaymentAuthorizeService payAuthorizeService;


    @Test public void  getOpenId(){
        String openId = payAuthorizeService.getOpenId("1000");
        System.out.println(openId);
    }


    @Test public void insertOpenId(){
        PaymentAuthorize paymentAuthorize = new PaymentAuthorize();
        paymentAuthorize.setOpenId("1000928272992923738229");
        paymentAuthorize.setUserNo("1000");
        payAuthorizeService.createOpenId(paymentAuthorize);
    }

}
