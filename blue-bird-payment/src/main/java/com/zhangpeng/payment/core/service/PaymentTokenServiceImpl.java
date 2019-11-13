package com.zhangpeng.payment.core.service;

import com.zhangpeng.payment.center.PaymentTokenService;
import com.zhangpeng.payment.center.domain.PaymentToken;
import com.zhangpeng.payment.core.mapper.PaymentTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("paymentTokenService")
public class PaymentTokenServiceImpl implements PaymentTokenService {

    @Autowired
    private PaymentTokenMapper paymentTokenMapper;

    @Override
    public void createPaymentToken(PaymentToken paymentToken) {
        paymentTokenMapper.insert(paymentToken);
    }

    @Override
    public void updatePaymentToken(PaymentToken paymentToken) {
        paymentTokenMapper.update(paymentToken);
    }

    @Override
    public void updatePaymentTokenStatus(String trxNo, String payStatus) {
        PaymentToken paymentToken = queryPaymentTokenByTrxNo(trxNo);
        paymentToken.setStatus(payStatus);
        updatePaymentToken(paymentToken);
    }

    @Override
    public PaymentToken queryPaymentToken(String merchantNo, String merchantOrderNo) {
        Map<String,Object> params = new HashMap<>();
        params.put("merchantNo",merchantNo);
        params.put("merchantOrderNo",merchantOrderNo);
        return paymentTokenMapper.selectBy(params);
    }

    @Override
    public PaymentToken queryPaymentTokenByTrxNo(String trxNo) {
        Map<String,Object> params = new HashMap<>();
        params.put("trxNo",trxNo);
        return paymentTokenMapper.selectBy(params);
    }

    @Override
    public PaymentToken queryPaymentTokenByTradeOrderNo(String tradeOrderNo) {
        Map<String,Object> params = new HashMap<>();
        params.put("bankOrderNo",tradeOrderNo);
        return paymentTokenMapper.selectBy(params);
    }
}
