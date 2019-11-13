package com.zhangpeng.payment.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhangpeng.payment.center.PayMDConfiguration;
import com.zhangpeng.payment.center.ex.PaymentBizException;
import com.zhangpeng.payment.center.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public final class MDPaymentUtils {


    /**
     * 秒到小程序支付
     * @param totalAmount
     * @param outTradeNo
     * @param notifyUrl
     * @return
     */
    public static JSONObject appletPay(String openId , String  payNo ,String totalAmount, String outTradeNo,String notifyUrl) {
        Map<String,String> param = new HashMap<>();
        param.put("totalAmount",totalAmount);
        param.put("productName","腾讯大王卡");
        param.put("merchantNo", PayMDConfiguration.MERCHANT_NO);
        param.put("outTradeNo",outTradeNo);
        param.put("appId", PayMDConfiguration.APP_ID);
        param.put("openId",openId);
        param.put("piType", PayMDConfiguration.PAY_WAY);
        param.put("gatewayPayMethod", PayMDConfiguration.ACCESS_MODE);
        param.put("agencyCode", PayMDConfiguration.ORGAN_NO);
        param.put("notifyUrl",notifyUrl);
        param.put("returnParams",payNo);
        String sign = null;
        try {
            sign = PaymentSign.signRequest(param, PayMDConfiguration.KEY);
        } catch (IOException e) {
            log.error("生成签名发生错误",e);
        }

        if(StringUtils.isBlank(sign)){
            throw new PaymentBizException(PaymentBizException.TRADE_PAY_SIGN_ERROR,"签名错误");
        }
        param.put("sign",sign);

        log.info("秒到秒到微信小程序统一下单，请求报文:{}", JSON.toJSONString(param));
        JSONObject jsonObject = null;
        try {
            jsonObject = HttpUtils.doPost(PayMDConfiguration.ORDER_URL, param);
        } catch (Exception e) {
            log.error("秒到下单接口异常: outTradeNo = {} , totalAmount = {} ",outTradeNo,totalAmount,e);
        }
        log.info("微信小程序统一下单，返回报文:{}", jsonObject.toJSONString());
        return jsonObject;
    }



    public  static  JSONObject openId(String successPageUrl){

        Map<String,String> param = new HashMap<>();
        param.put("merchantNo", PayMDConfiguration.MERCHANT_NO);
        param.put("piType", PayMDConfiguration.PAY_WAY);
        param.put("successPageUrl",successPageUrl);
        String sign = null;
        try {
            sign = PaymentSign.signRequest(param, PayMDConfiguration.KEY);
        } catch (IOException e) {
            log.error("生成签名发生错误",e);
        }

        if(StringUtils.isBlank(sign)){
            throw new PaymentBizException(PaymentBizException.TRADE_PAY_SIGN_ERROR,"签名错误");
        }
        param.put("sign",sign);


        log.info("秒到微信小程序获取openid，请求报文:{}", JSON.toJSONString(param));
        JSONObject jsonObject = null;
        try {
            jsonObject = HttpUtils.doPost(PayMDConfiguration.OPENID_URL, param);
        } catch (Exception e) {
            log.error("秒到获取openid接口异常 ",e);
        }
        log.info("微信小程序获取openid，返回报文:{}", jsonObject.toJSONString());
        return jsonObject;
    }


    public  static  JSONObject queryOrder(String outTradeNo){
        Map<String,String> param = new HashMap<>();
        param.put("merchantNo", PayMDConfiguration.MERCHANT_NO);
        param.put("outTradeNo", outTradeNo);

        String sign = null;
        try {
            sign = PaymentSign.signRequest(param, PayMDConfiguration.KEY);
        } catch (IOException e) {
            log.error("生成签名发生错误",e);
        }

        param.put("sign",sign);

        log.info("秒到查询订单，请求报文:{}", JSON.toJSONString(param));
        JSONObject jsonObject = null;
        try {
            jsonObject = HttpUtils.doPost(PayMDConfiguration.ORDER_QUERY_URL, param);
        } catch (Exception e) {
            log.error("秒到查询订单接口异常 ",e);
        }
        log.info("秒到查询订单，返回报文:{}", jsonObject.toJSONString());
        return jsonObject;
    }



    public static void main(String[] args) {
        try {
            //JSONObject jsonObject = appletPay("","0.01", "1000100001001", "www.baidu.com");
            JSONObject jsonObject = openId("www.baidu.com");
            System.out.println(jsonObject.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
