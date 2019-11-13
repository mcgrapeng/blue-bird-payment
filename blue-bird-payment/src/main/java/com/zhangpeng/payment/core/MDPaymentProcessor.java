package com.zhangpeng.payment.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.zhangpeng.payment.center.PaymentREQ;
import com.zhangpeng.payment.center.PaymentRES;
import com.zhangpeng.payment.center.domain.PaymentWay;
import com.zhangpeng.payment.center.enums.PayTypeEnum;
import com.zhangpeng.payment.center.enums.PayWayEnum;
import com.zhangpeng.payment.center.ex.PaymentBizException;
import com.zhangpeng.payment.core.utils.MDPaymentUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

@Slf4j
@Service("MDPaymentProcessor")
public class MDPaymentProcessor extends PaymentProcessorTemplate  {
    @Override
    public PaymentRES process(PaymentWay paymentWay, PaymentREQ paymentREQ) {
        if (PayWayEnum.MIAODAO.name().equals(paymentWay.getPayWayCode())) {
            JSONObject jsonObject = null;
            try {
                if (paymentWay.getPayTypeCode().equals(PayTypeEnum.WX_PROGRAM_PAY.name())
                        || paymentWay.getPayTypeCode().equals(PayTypeEnum.WX_ACCOUNT_PAY.name())) {
                    //生成预付单，向秒到发起
                    jsonObject = MDPaymentUtils.appletPay(paymentREQ.getOpenId(), paymentREQ.getTrxNo(), paymentREQ.getOrderAmount().toPlainString()
                            , paymentREQ.getMerchantOrderNo(), paymentREQ.getNotifyUrl());
                    //验签
                }
            } catch (Exception e) {
                log.error("秒到统一下单发生异常，paymentREQ = {} ", JSON.toJSONString(paymentREQ), e);
                return PaymentRES.of(String.valueOf(PaymentBizException.FAILED)
                        , "网络异常，请稍后重试");
            }
            if (null != jsonObject
                    && jsonObject.getBoolean("result") && jsonObject.getInteger("code") == 200) {
                return PaymentRES.of(String.valueOf(PaymentBizException.SUCCESS), jsonObject, "支付成功!");
            }
        }
        return PaymentRES.of(String.valueOf(PaymentBizException.FAILED), "支付失败!");
    }

    @Override
    public PaymentRES openid(PaymentREQ paymentREQ) {
        Map<String,String> data = Maps.newHashMap();
        JSONObject jsonObject;
        try {
            String openId = paymentAuthorizeService.getOpenId(paymentREQ.getUserNo());
            if(StringUtils.isNotBlank(openId)){
                data.put("openId",openId);
                return PaymentRES.of(String.valueOf(PaymentBizException.SUCCESS), data, "获取openid成功!");
            }
            jsonObject = MDPaymentUtils.openId(paymentREQ.getOpenidUrl());
            if(null != jsonObject
                    &&  jsonObject.getBoolean("result")  && jsonObject.getInteger("code") == 200){
                String redirectUrl = jsonObject.getString("redirectUrl");
                data.put("redirectUrl",redirectUrl);
                return PaymentRES.of(String.valueOf(PaymentBizException.SUCCESS), data, "重定向请求发起");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return PaymentRES.of(String.valueOf(PaymentBizException.FAILED), "获取openid失败!");
    }
}
