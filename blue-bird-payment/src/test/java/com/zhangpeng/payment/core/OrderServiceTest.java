package com.zhangpeng.payment.core;

import com.alibaba.fastjson.JSON;
import com.zhangpeng.payment.center.PaymentOrderService;
import com.zhangpeng.payment.center.domain.PaymentOrder;
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
public class OrderServiceTest {

    @Autowired
    private PaymentOrderService paymentOrderService;

    @Test  public void paymentOrderQuery(){
        PaymentOrder paymentOrder = paymentOrderService.selectByMerchantNoAndMerchantOrderNo("1", "1");

        System.out.println(JSON.toJSONString(paymentOrder));
    }



    @Test public void createPaymentOrder(){

        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setStatus(TradeStatusEnum.SUCCESS.name());

        paymentOrder.setCreater("张朋");
        paymentOrder.setEditor("张朋");
        paymentOrder.setEditTime(new Date());
        paymentOrder.setRemark("备注");

        paymentOrder.setOrderAmount(new BigDecimal("100.00"))
                .setExpireTime(new Date())
                .setMerchantName("卡卡得")
                .setMerchantNo("35")
                .setMerchantOrderNo("10010001000333")
                .setNotifyUrl("www.baidu.com")
                .setOrderDate("2019-10-28")
                .setOrderIp("127.0.0.1")
                .setOrderPeriod(15)
                .setOrderRefererUrl("www.baidu.com")
                .setOrderTime("2019-10-28 10:00:00")
                .setPayTypeCode("10")
                .setPayTypeName("小程序微信支付")
                .setPayWayName("微信")
                .setPayWayCode("01")
                .setProductName("腾讯大王卡")
                .setReturnUrl("www.baidu.com")
                .setTrxNo("1000000000")
                .setCancelReason("不买了")
                .setOrderFrom("用户消费")
                .setRefund(Boolean.FALSE)
                .setRefundTimes((short)0)
                .setSuccessRefundAmount(BigDecimal.ZERO)
                .setTrxType("消费");
        paymentOrderService.createPaymentOrder(paymentOrder);

    }


    @Test public  void  updatePaymentOrder(){

        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setStatus(TradeStatusEnum.SUCCESS.name());
        paymentOrder.setId(1);
        paymentOrder.setCreater("张朋2");
        paymentOrder.setEditor("张朋");
        paymentOrder.setEditTime(new Date());
        paymentOrder.setRemark("备注");

        paymentOrder.setOrderAmount(new BigDecimal("100.00"))
                .setExpireTime(new Date())
                .setMerchantName("卡卡得2")
                .setMerchantNo("35")
                .setMerchantOrderNo("10010001000")
                .setNotifyUrl("www.baidu.com")
                .setOrderDate("2019-10-28")
                .setOrderIp("127.0.0.1")
                .setOrderPeriod(15)
                .setOrderRefererUrl("www.baidu.com")
                .setOrderTime("2019-10-28 10:00:00")
                .setPayTypeCode("10")
                .setPayTypeName("小程序微信支付")
                .setPayWayName("微信")
                .setPayWayCode("01")
                .setProductName("腾讯大王卡")
                .setReturnUrl("www.baidu.com")
                .setTrxNo("1000000000")
                .setCancelReason("不买了2222")
                .setOrderFrom("用户消费")
                .setRefund(Boolean.FALSE)
                .setRefundTimes((short)0)
                .setSuccessRefundAmount(BigDecimal.ZERO)
                .setTrxType("消费");
        paymentOrderService.updatePaymentOrder(paymentOrder);

    }


    @Test public void updatePaymentOrderStatus(){
        paymentOrderService.updatePaymentOrderStatus("35","10010001000",TradeStatusEnum.FAILED.name());
    }

}
