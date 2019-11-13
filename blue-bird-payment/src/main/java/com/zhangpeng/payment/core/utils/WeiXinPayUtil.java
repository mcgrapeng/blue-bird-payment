package com.zhangpeng.payment.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.zhangpeng.payment.core.domain.TransferProductDetails;
import com.zhangpeng.payment.core.enums.WeiXinTradeTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.*;
@Slf4j
public final class WeiXinPayUtil {

    private WeiXinPayUtil() {

    }


    /**
     * 公众号/小程序/APP统一下单
     * @return
     */
    public static Map<String, Object> unifiedOrder(WeiXinTradeTypeEnum tradeTypeEnum,String outTradeNo, String body, BigDecimal totalAmount, String spbillCreateIp
            , String notifyUrl, String openid, List<TransferProductDetails> transferProductDetails) {
        String nonce_str = getnonceStr();
        Integer totalFee = totalAmount.multiply(BigDecimal.valueOf(100L)).intValue();

        SortedMap<String, Object> paramMap = new TreeMap<>();
        paramMap.put("appid", WXConfigUtils.xAppId);
        paramMap.put("mch_id", WXConfigUtils.xMchId);
        paramMap.put("device_info", nonce_str);
        paramMap.put("nonce_str", nonce_str);
        paramMap.put("sign_type", "MD5");
        paramMap.put("body", body);
        paramMap.put("out_trade_no", outTradeNo);
        paramMap.put("total_fee", totalFee);
        paramMap.put("spbill_create_ip", spbillCreateIp);
        paramMap.put("notify_url", notifyUrl);

        if(tradeTypeEnum.name().equals(WeiXinTradeTypeEnum.JSAPI.name())){//小程序/公众号
            paramMap.put("trade_type", WeiXinTradeTypeEnum.JSAPI.name());
        }else if(tradeTypeEnum.name().equals(WeiXinTradeTypeEnum.APP.name())){//APP
            paramMap.put("trade_type", WeiXinTradeTypeEnum.APP.name());
        }

        paramMap.put("fee_type", "CNY");
        paramMap.put("openid", openid);
        if (!CollectionUtils.isEmpty(transferProductDetails)) {
            List<SortedMap<String, Object>> goodList = new ArrayList<>();
            for (TransferProductDetails detail : transferProductDetails) {
                SortedMap<String, Object> goodsDetailMap = new TreeMap<>();
                goodsDetailMap.put("goods_id", detail.getGoodsId());
                goodsDetailMap.put("quantity", detail.getQuantity());
                goodsDetailMap.put("goods_name", detail.getGoodsName());
                goodsDetailMap.put("price", detail.getPrice());
                goodList.add(goodsDetailMap);
            }
            JSONObject goodsDetailJson = new JSONObject();
            goodsDetailJson.put("goods_detail", goodList);
            paramMap.put("detail", goodsDetailJson.toJSONString());
        }
        paramMap.put("sign", getSign(paramMap, WXConfigUtils.xPayKey));
        String data = mapToXml(paramMap);
        log.info("微信小程序统一下单，请求报文:{}", data);
        Map<String, Object> resultMap = WeiXinPayUtils.httpXmlRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", "POST", data);
        log.info("微信小程序统一下单，返回报文:{}", resultMap);
        if (resultMap == null || resultMap.isEmpty()) {
            return null;
        }
        SortedMap<String, Object> responseMap = new TreeMap<>();
        responseMap.putAll(resultMap);
        String resultSign = getSign(responseMap, WXConfigUtils.xPayKey);
        if (resultSign.equals(resultMap.get("sign"))) {
            resultMap.put("verify", "YES");
        } else {
            log.info("返回报文验签失败,返回报文签名:{},返回签名:{}", resultSign, resultMap.get("sign"));
            resultMap.put("verify", "NO");
        }
        return resultMap;
    }



    /**
     * 生成随机字符串
     *
     * @return
     */
    private static String getnonceStr() {
        Random random = new Random();
        StringBuilder nonceStrBuilder = new StringBuilder();
        for (int i = 0; i < 31; i++) {
            nonceStrBuilder.append(random.nextInt(10));
        }
        return nonceStrBuilder.toString();
    }

    /**
     * 签名
     *
     * @param paramMap
     * @param key
     * @return
     */
    private static String getSign(SortedMap<String, Object> paramMap, String key) {
        StringBuilder signBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            if (!"sign".equals(entry.getKey()) && !ObjectUtils.isEmpty(entry.getValue())) {
                signBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        signBuilder.append("key=").append(key);
        log.info("微信待签名参数字符串:{}", signBuilder.toString());
        return MD5Utils.encode(signBuilder.toString()).toUpperCase();
    }

    /**
     * 转xml格式
     * @param paramMap
     * @return
     */
    private static String mapToXml(SortedMap<String, Object> paramMap) {
        StringBuilder dataBuilder = new StringBuilder("<xml>");
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            if (!ObjectUtils.isEmpty(entry.getValue())) {
                dataBuilder.append("<").append(entry.getKey()).append(">").append(entry.getValue()).append("</").append(entry.getKey()).append(">");
            }
        }
        dataBuilder.append("</xml>");
        log.info("Map转Xml结果:{}", dataBuilder.toString());
        return dataBuilder.toString();
    }

}
