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
                return PaymentRES.of(PaymentBizException.FAILED
                        , "网络异常，请稍后重试");
            }
            if (null != jsonObject
                    && jsonObject.getBoolean("result") && jsonObject.getInteger("code") == 200) {
                JSONObject data = jsonObject.getJSONObject("data");

                //"{"appId":"wxa3da6581820b3143","timeStamp":"1578884425","nonceStr":"2d34a944726346d1ba76cd512559e4ac","package":"prepay_id=wx131100257417319dcf9ffe491922423700","signType":"RSA","paySign":"EVHYxZvBOKb/sjrb3y9aLpAf0oLjBYNGdD2uucJsrCBmU/YVoIyq2H7m6LsoEOBHkjvbM9efTNVS0kjt0sqFeENwxmc4cXVUDtJaYTnnYALw2RbyPOn2K949L85wbQ0W2Vngg9MD5Awzh1PJhwhw9CgcmKkj90rmDkKfuu8OFOm1Ly3SO6YAeirVzNLlylybydVd3T/6HxVbtaoWnT9XhrB+pUxwYUgYWpsxLNdD0fQdSLu3IZhR+G+bL2LKXyDuuT9j5yMfaA7CQfs67nAMip79AeNpals8VVjD3f5hNAqqLpgc/mdTJP3qdsy+YeVWzSCLCZZy+aVHJgNsEHcKGQ=="}"

                String appId = data.getString("appId");
                String timeStamp = data.getString("timeStamp");
                String nonceStr = data.getString("nonceStr");
                String prepayId = data.getString("package");


                return PaymentRES.of(PaymentBizException.SUCCESS, data, "支付成功!");
            }
        }
        return PaymentRES.of(PaymentBizException.FAILED, "支付失败!");
    }
}
