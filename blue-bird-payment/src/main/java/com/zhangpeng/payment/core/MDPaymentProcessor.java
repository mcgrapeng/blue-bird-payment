package com.zhangpeng.payment.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhangpeng.payment.center.PaymentREQ;
import com.zhangpeng.payment.center.PaymentRES;
import com.zhangpeng.payment.center.domain.PaymentWay;
import com.zhangpeng.payment.center.enums.MDPayConfigEnum;
import com.zhangpeng.payment.center.enums.PayTypeEnum;
import com.zhangpeng.payment.center.enums.PayWayEnum;
import com.zhangpeng.payment.center.ex.PaymentBizException;
import com.zhangpeng.payment.core.utils.MDPaymentUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("MDPaymentProcessor")
public class MDPaymentProcessor extends PaymentProcessorTemplate {

    @Override
    public PaymentRES process(PaymentWay paymentWay, PaymentREQ paymentREQ) {
        if (PayWayEnum.MIAODAO.name().equals(paymentWay.getPayWayCode())) {
            JSONObject jsonObject = null;
            try {
                if (paymentWay.getPayTypeCode().equals(PayTypeEnum.WX_PROGRAM_PAY.name())) {
                    //生成预付单，向秒到发起
                    jsonObject = MDPaymentUtils.pay(MDPayConfigEnum.MD_WX_PROGRAM_PAY.getMerchantNo()
                            , MDPayConfigEnum.MD_WX_PROGRAM_PAY.getAppId(), MDPayConfigEnum.MD_WX_PROGRAM_PAY.getAccessMode(), MDPayConfigEnum.MD_WX_PROGRAM_PAY.getKey()
                            , MDPayConfigEnum.MD_WX_PROGRAM_PAY.getPiType(),  paymentREQ.getOpenId(),paymentREQ.getTrxNo(), paymentREQ.getOrderAmount().toPlainString()
                            , paymentREQ.getMerchantOrderNo(), paymentREQ.getNotifyUrl());
                }else if(paymentWay.getPayTypeCode().equals(PayTypeEnum.WX_ACCOUNT_PAY.name())){
                    jsonObject = MDPaymentUtils.pay(MDPayConfigEnum.MD_WX_ACCOUNT_PAY.getMerchantNo()
                            , MDPayConfigEnum.MD_WX_ACCOUNT_PAY.getAppId(), MDPayConfigEnum.MD_WX_ACCOUNT_PAY.getAccessMode(), MDPayConfigEnum.MD_WX_ACCOUNT_PAY.getKey()
                            , MDPayConfigEnum.MD_WX_ACCOUNT_PAY.getPiType(),  paymentREQ.getOpenId(),paymentREQ.getTrxNo(), paymentREQ.getOrderAmount().toPlainString()
                            , paymentREQ.getMerchantOrderNo(), paymentREQ.getNotifyUrl());
                }
            } catch (Exception e) {
                log.error("秒到统一下单发生异常，paymentREQ = {} ", JSON.toJSONString(paymentREQ), e);
                return PaymentRES.of(String.valueOf(PaymentBizException.FAILED)
                        , "网络异常，请稍后重试");
            }
            if (null != jsonObject
                    && jsonObject.getBoolean("result") && jsonObject.getInteger("code") == 200) {
                JSONObject data = jsonObject.getJSONObject("data");
                return PaymentRES.of(String.valueOf(PaymentBizException.SUCCESS), data, "支付成功!");
            }
        }
        return PaymentRES.of(String.valueOf(PaymentBizException.FAILED), "支付失败!");
    }

/*
    @Override
    public PaymentRES payOpenId(PaymentREQ paymentREQ) {
        Map<String,String> data = Maps.newHashMap();
        JSONObject jsonObject = null;
        try {
            String openId = paymentAuthorizeService.getOpenId(paymentREQ.getUserNo());
            if(StringUtils.isNotBlank(openId)){
                data.put("openId",openId);
                return PaymentRES.of(String.valueOf(PaymentBizException.SUCCESS), data, "获取openid成功!");
            }
            if (paymentREQ.getPayType().equals(PayTypeEnum.WX_PROGRAM_PAY.name())) {
                jsonObject = MDPaymentUtils.openId(paymentREQ.getOpenidUrl(),MDPayConfigEnum.MD_WX_PROGRAM_PAY.getPiType()
                        ,MDPayConfigEnum.MD_WX_PROGRAM_PAY.getMerchantNo(), MDPayConfigEnum.MD_WX_PROGRAM_PAY.getKey());
            }else if(paymentREQ.getPayType().equals(PayTypeEnum.WX_ACCOUNT_PAY.name())){
                jsonObject = MDPaymentUtils.openId(paymentREQ.getOpenidUrl(),MDPayConfigEnum.MD_WX_ACCOUNT_PAY.getPiType()
                        ,MDPayConfigEnum.MD_WX_ACCOUNT_PAY.getMerchantNo(),MDPayConfigEnum.MD_WX_ACCOUNT_PAY.getKey());
            }
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
*/

}
