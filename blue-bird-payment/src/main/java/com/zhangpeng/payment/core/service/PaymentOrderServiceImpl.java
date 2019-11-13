package com.zhangpeng.payment.core.service;

import com.zhangpeng.payment.center.PaymentOrderService;
import com.zhangpeng.payment.center.domain.PaymentOrder;
import com.zhangpeng.payment.core.mapper.PaymentOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("paymentOrderService")
public class PaymentOrderServiceImpl implements PaymentOrderService {

    @Autowired
    private PaymentOrderMapper paymentOrderMapper;

    @Override
    public PaymentOrder selectByMerchantNoAndMerchantOrderNo(String merchantNo, String merchantOrderNo) {
        Map<String , Object> paramMap = new HashMap<String , Object>();
        paramMap.put("merchantNo",merchantNo);
        paramMap.put("merchantOrderNo",merchantOrderNo);
        return paymentOrderMapper.selectBy(paramMap);
    }

    @Override
    public void createPaymentOrder(PaymentOrder paymentOrder) {
        paymentOrderMapper.insert(paymentOrder);
    }

    @Override
    public void updatePaymentOrder(PaymentOrder paymentOrder) {
        paymentOrderMapper.update(paymentOrder);
    }

    @Override
    public void updatePaymentOrderStatus(String merchantNo, String merchantOrderNo, String orderStatus) {
        PaymentOrder paymentOrder = selectByMerchantNoAndMerchantOrderNo( merchantNo,  merchantOrderNo);
        paymentOrder.setStatus(orderStatus);
        updatePaymentOrder(paymentOrder);
    }


}
