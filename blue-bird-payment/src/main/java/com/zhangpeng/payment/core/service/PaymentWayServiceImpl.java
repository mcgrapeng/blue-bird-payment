package com.zhangpeng.payment.core.service;

import com.zhangpeng.payment.center.PaymentWayService;
import com.zhangpeng.payment.center.domain.PaymentWay;
import com.zhangpeng.payment.center.enums.PublicStatusEnum;
import com.zhangpeng.payment.core.mapper.PaymentWayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("paymentWayService")
public class PaymentWayServiceImpl implements PaymentWayService {

    @Autowired
    private PaymentWayMapper paymentWayMapper;

    @Override
    public PaymentWay getPaymentWay(String payProductCode, String payWayCode, String payTypeCode) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("payProductCode", payProductCode);
        paramMap.put("payTypeCode", payTypeCode);
        paramMap.put("payWayCode", payWayCode);
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        return paymentWayMapper.selectBy(paramMap);
    }
}
