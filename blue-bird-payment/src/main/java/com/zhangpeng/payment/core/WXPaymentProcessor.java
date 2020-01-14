package com.zhangpeng.payment.core;

import com.google.common.collect.Maps;
import com.zhangpeng.payment.center.PaymentREQ;
import com.zhangpeng.payment.center.PaymentRES;
import com.zhangpeng.payment.center.domain.PaymentWay;
import com.zhangpeng.payment.center.enums.PayWayEnum;
import com.zhangpeng.payment.center.ex.PaymentBizException;
import com.zhangpeng.payment.core.enums.WeiXinTradeTypeEnum;
import com.zhangpeng.payment.core.utils.MD5;
import com.zhangpeng.payment.core.utils.WXPaymentUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service("WXPaymentProcessor")
public class WXPaymentProcessor extends  PaymentProcessorTemplate  {

    @Override
    public PaymentRES process(PaymentWay paymentWay, PaymentREQ paymentREQ) {
        if (PayWayEnum.WEIXIN.name().equals(paymentWay.getPayWayCode())) {
            Map<String, Object> ret = WXPaymentUtils.unifiedOrder(WeiXinTradeTypeEnum.JSAPI, paymentREQ.getTrxNo(),paymentREQ.getMerchantOrderNo(), "联通5G冰淇淋套餐"
                    , paymentREQ.getOrderAmount(), paymentREQ.getOrderIp(), paymentREQ.getNotifyUrl(), paymentREQ.getOpenId(), null);

            String prepayId = (String) ret.get("prepay_id");

            String nonce_str = (String) ret.get("nonce_str");
            String appid = (String) ret.get("appid");
            long timeStamp = System.currentTimeMillis();
            String key="zhangpeng1ZHANGpeng2198905121989";
            String signType = "MD5";
            String str = "appId="+appid+"&nonceStr=" +nonce_str+
                    "&package=prepay_id="+prepayId+"&signType="+signType+"&timeStamp="+timeStamp+"&key="+key;
            String paySign = MD5.encode(str);

            Map<String,String>  data = Maps.newHashMap();
            data.put("package","prepay_id="+prepayId);
            data.put("nonceStr",nonce_str);
            data.put("timeStamp",String.valueOf(timeStamp));
            data.put("signType",signType);
            data.put("paySign",paySign);
            return PaymentRES.of(PaymentBizException.SUCCESS,data,"支付成功!");
        }
        return PaymentRES.of(PaymentBizException.FAILED,"支付失败!");
    }
}

