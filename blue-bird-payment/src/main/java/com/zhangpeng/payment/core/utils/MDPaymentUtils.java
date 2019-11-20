package com.zhangpeng.payment.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhangpeng.payment.center.PayMDConfiguration;
import com.zhangpeng.payment.center.ex.PaymentBizException;
import com.zhangpeng.payment.center.utils.HttpUtils;
import com.zhangpeng.payment.core.enums.MDPayConfigEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public final class MDPaymentUtils {


    /**
     * 秒到统一下单
     *
     * @param totalAmount
     * @param outTradeNo
     * @param notifyUrl
     * @return
     */
    public static JSONObject pay(String merchantNo, String appId, String gatewayPayMethod, String key
            , String piType, String openId, String payNo, String totalAmount, String outTradeNo, String notifyUrl) {
        Map<String, String> param = new HashMap<>();
        param.put("totalAmount", totalAmount);
        param.put("productName", "腾讯大王卡");
        param.put("merchantNo", merchantNo);
        param.put("outTradeNo", outTradeNo);
        param.put("appId", appId);
        param.put("openId", openId);
        param.put("piType", piType);
        param.put("gatewayPayMethod", gatewayPayMethod);
        param.put("agencyCode", PayMDConfiguration.ORGAN_NO);
        param.put("notifyUrl", notifyUrl);
        param.put("returnParams", payNo);
        String sign = null;
        try {
            sign = MDPaymentSign.signRequest(param, key);
        } catch (IOException e) {
            log.error("生成签名发生错误", e);
        }

        if (StringUtils.isBlank(sign)) {
            throw new PaymentBizException(PaymentBizException.TRADE_PAY_SIGN_ERROR, "签名错误");
        }
        param.put("sign", sign);

        log.info("秒到秒到微信小程序统一下单，请求报文:{}", JSON.toJSONString(param));
        JSONObject jsonObject = null;
        try {
            jsonObject = HttpUtils.doPost(PayMDConfiguration.ORDER_URL, param);
        } catch (Exception e) {
            log.error("秒到下单接口异常: outTradeNo = {} , totalAmount = {} ", outTradeNo, totalAmount, e);
        }
        log.info("微信小程序统一下单，返回报文:{}", jsonObject.toJSONString());
        return jsonObject;
    }


    /**
     * 秒到获取openid
     *
     * @param successPageUrl
     * @return
     */
    public static JSONObject openId(String successPageUrl,String piType,String merchantNo,String key) {

        Map<String, String> param = new HashMap<>();
        param.put("merchantNo", merchantNo);
        param.put("piType", piType);
        param.put("successPageUrl", successPageUrl);
        String sign = null;
        try {
            sign = MDPaymentSign.signRequest(param, key);
        } catch (IOException e) {
            log.error("生成签名发生错误", e);
        }

        if (StringUtils.isBlank(sign)) {
            throw new PaymentBizException(PaymentBizException.TRADE_PAY_SIGN_ERROR, "签名错误");
        }
        param.put("sign", sign);


        log.info("秒到微信小程序获取openid，请求报文:{}", JSON.toJSONString(param));
        JSONObject jsonObject = null;
        try {
            jsonObject = HttpUtils.doPost(PayMDConfiguration.OPENID_URL, param);
        } catch (Exception e) {
            log.error("秒到获取openid接口异常 ", e);
        }
        log.info("微信小程序获取openid，返回报文:{}", jsonObject.toJSONString());
        return jsonObject;
    }


    /**
     * 秒到订单查询
     *
     * @param outTradeNo
     * @return
     */
    public static JSONObject queryOrder(String merchantNo,String outTradeNo,String key) {
        Map<String, String> param = new HashMap<>();
        param.put("merchantNo", merchantNo);
        param.put("outTradeNo", outTradeNo);

        String sign = null;
        try {
            sign = MDPaymentSign.signRequest(param, key);
        } catch (IOException e) {
            log.error("生成签名发生错误", e);
        }

        param.put("sign", sign);

        log.info("秒到查询订单，请求报文:{}", JSON.toJSONString(param));
        JSONObject jsonObject = null;
        try {
            jsonObject = HttpUtils.doPost(PayMDConfiguration.ORDER_QUERY_URL, param);
        } catch (Exception e) {
            log.error("秒到查询订单接口异常 ", e);
        }
        log.info("秒到查询订单，返回报文:{}", jsonObject.toJSONString());
        return jsonObject;
    }


    /**
     * 关闭订单
     *
     * @param outTradeNo
     * @return
     */
    public static JSONObject closeOrder(String merchantNo,String outTradeNo,String key) {
        Map<String, String> param = new HashMap<>();
        param.put("merchantNo", merchantNo);
        param.put("outTradeNo", outTradeNo);
        param.put("agencyCode", PayMDConfiguration.ORGAN_NO);
        String sign = null;
        try {
            sign = MDPaymentSign.signRequest(param, key);
        } catch (IOException e) {
            log.error("生成签名发生错误", e);
        }
        param.put("sign", sign);
        log.info("秒到关闭订单，请求报文:{}", JSON.toJSONString(param));
        JSONObject jsonObject = null;
        try {
            jsonObject = HttpUtils.doPost(PayMDConfiguration.CLOSE_ORDER_URL, param);
        } catch (Exception e) {
            log.error("秒到关闭订单接口异常 ", e);
        }
        log.info("秒到关闭订单，返回报文:{}", jsonObject.toJSONString());
        return jsonObject;
    }


    public static void main(String[] args) {
        try {
            JSONObject jsonObject =  pay(MDPayConfigEnum.MD_WX_ACCOUNT_PAY.getMerchantNo()
                    , MDPayConfigEnum.MD_WX_ACCOUNT_PAY.getAppId()
                    , MDPayConfigEnum.MD_WX_ACCOUNT_PAY.getAccessMode()
                    , MDPayConfigEnum.MD_WX_ACCOUNT_PAY.getKey()
                    , MDPayConfigEnum.MD_WX_ACCOUNT_PAY.getPayType()
                    , "ogbn-1dwuwAdD8Bqh0pLo2abiO2g", "6798765111111", "0.01", "1573784350393"
                    , "www.baidu.com");

            //   JSONObject jsonObject = openId("www.baidu.com");

            System.out.println(jsonObject);
            System.out.println("=====================");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
