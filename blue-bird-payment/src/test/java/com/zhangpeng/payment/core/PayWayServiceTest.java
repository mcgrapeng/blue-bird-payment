package com.zhangpeng.payment.core;


import com.alibaba.fastjson.JSON;
import com.zhangpeng.payment.center.PaymentWayService;
import com.zhangpeng.payment.center.domain.PaymentWay;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/spring-*.xml"})
public class PayWayServiceTest {

    @Autowired
    private PaymentWayService paymentWayService;

    @Test public void getPaymentWay(){
        PaymentWay paymentWay = paymentWayService.getPaymentWay("35"
        ,"WEIXIN","WX_PROGRAM_PAY");
        System.out.println(JSON.toJSONString(paymentWay));
    }

}
