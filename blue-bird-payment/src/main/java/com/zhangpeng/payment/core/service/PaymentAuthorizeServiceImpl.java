package com.zhangpeng.payment.core.service;

import com.google.common.collect.Maps;
import com.zhangpeng.payment.center.PaymentAuthorizeService;
import com.zhangpeng.payment.center.domain.PaymentAuthorize;
import com.zhangpeng.payment.core.mapper.PaymentAuthorizeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("paymentAuthorizeService")
public class PaymentAuthorizeServiceImpl implements PaymentAuthorizeService {

    @Autowired
    private PaymentAuthorizeMapper paymentAuthorizeMapper;

    @Override
    public String getOpenId(String userNo) {
        Map<String,Object> params = Maps.newHashMap();
        params.put("userNo",userNo);
        PaymentAuthorize paymentAuthorize = paymentAuthorizeMapper.selectBy(params);
        if(null != paymentAuthorize){
            return paymentAuthorize.getOpenId();
        }
        return null;
    }

    @Override
    public void createOpenId(PaymentAuthorize paymentAuthorize) {
        paymentAuthorizeMapper.insert(paymentAuthorize);
    }

    @Override
    public void deleteOpenId(String userNo) {
        paymentAuthorizeMapper.deleteByUserNo(userNo);
    }
}
