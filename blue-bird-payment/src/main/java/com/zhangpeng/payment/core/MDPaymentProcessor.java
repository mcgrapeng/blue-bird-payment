package com.zhangpeng.payment.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.zhangpeng.payment.center.PayMDConfiguration;
import com.zhangpeng.payment.center.PaymentAuthorizeProcessor;
import com.zhangpeng.payment.center.PaymentREQ;
import com.zhangpeng.payment.center.PaymentRES;
import com.zhangpeng.payment.center.domain.PaymentWay;
import com.zhangpeng.payment.center.enums.PayTypeEnum;
import com.zhangpeng.payment.center.enums.PayWayEnum;
import com.zhangpeng.payment.center.ex.PaymentBizException;
import com.zhangpeng.payment.center.utils.DateUtils;
import com.zhangpeng.payment.core.enums.SecurityEnum;
import com.zhangpeng.payment.core.utils.MDPaymentUtils;
import com.zhangpeng.payment.core.utils.WXCommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Slf4j
@Service("MDPaymentProcessor")
public class MDPaymentProcessor extends PaymentProcessorTemplate implements PaymentAuthorizeProcessor {
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
    public PaymentRES payOpenId(PaymentREQ paymentREQ) {
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
                String redirectUrl = jsonObject.getJSONObject("data").getString("redirectUrl");
                data.put("redirectUrl",redirectUrl);
                return PaymentRES.of(String.valueOf(PaymentBizException.SUCCESS), data, "重定向请求发起");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return PaymentRES.of(String.valueOf(PaymentBizException.FAILED), "获取openid失败!");
    }

    @Override
    public PaymentRES paySign(PaymentREQ paymentREQ) {
        String param = (String)paymentREQ.getParam();
        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("appId",PayMDConfiguration.APP_ID);
        paramMap.put("timeStamp",String.valueOf(DateUtils.getSecondTimestamp(new Date())));
        paramMap.put("nonceStr",WXCommonUtils.createNonceStr());
        paramMap.put("signType", SecurityEnum.MD5.getDesc());
        paramMap.put("package",param);
        paramMap.put("paySign",WXCommonUtils.md5Sign(paramMap, PayMDConfiguration.KEY));
        return PaymentRES.of(String.valueOf(PaymentBizException.SUCCESS), paramMap, "获取签名成功!");
    }
}
