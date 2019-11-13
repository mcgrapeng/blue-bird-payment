package com.zhangpeng.payment.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhangpeng.payment.center.PaymentREQ;
import com.zhangpeng.payment.center.PaymentRES;
import com.zhangpeng.payment.center.domain.PaymentWay;
import com.zhangpeng.payment.center.enums.PayTypeEnum;
import com.zhangpeng.payment.center.enums.PayWayEnum;
import com.zhangpeng.payment.center.ex.PaymentBizException;
import com.zhangpeng.payment.core.utils.MDPaymentUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Slf4j
@Service("WXPaymentProcessor")
public class WXPaymentProcessor extends  PaymentProcessorTemplate  {
    @Override
    public PaymentRES process(PaymentWay paymentWay, PaymentREQ paymentREQ) {
        if (PayWayEnum.WEIXIN.name().equals(paymentWay.getPayWayCode())) {
            JSONObject jsonObject = null;
            try {
                if(paymentWay.getPayTypeCode().equals(PayTypeEnum.WX_ACCOUNT_PAY.name())){
                    //生成预付单，向秒到/微信发起
                    jsonObject = MDPaymentUtils.appletPay(paymentREQ.getOpenId(),paymentREQ.getTrxNo(),paymentREQ.getOrderAmount().toPlainString()
                            , paymentREQ.getMerchantOrderNo(), paymentREQ.getNotifyUrl());

                    //验签
                }
            } catch (Exception e) {
                log.error("秒到统一下单发生异常，paymentREQ = {} " , JSON.toJSONString(paymentREQ),e );
                return PaymentRES.of(String.valueOf(PaymentBizException.FAILED)
                        , "网络异常，请稍后重试");
            }
            if(null != jsonObject
                    &&  jsonObject.getBoolean("result")  && jsonObject.getInteger("code") == 200){
                return  PaymentRES.of(String.valueOf(PaymentBizException.SUCCESS),jsonObject,"支付成功!");
            }
        }
        return PaymentRES.of(String.valueOf(PaymentBizException.FAILED),"支付失败!");
    }

    @Override
    public PaymentRES openid( PaymentREQ paymentREQ) {

        //String authUrl = WXConfigUtils.xAuthUrl.replace("{APPID}", WXConfigUtils.xAppId).replace("{SECRET}", WXConfigUtils.xPartnerKey).replace("{JSCODE}", code).replace("{GRANTTYPE}", WXConfigUtils.xGrantType);

        return null;
    }
}
