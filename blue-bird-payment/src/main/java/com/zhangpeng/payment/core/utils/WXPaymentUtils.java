package com.zhangpeng.payment.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.zhangpeng.payment.core.domain.TransferProductDetails;
import com.zhangpeng.payment.core.enums.SecurityEnum;
import com.zhangpeng.payment.core.enums.WeiXinTradeTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
@Slf4j
public final class WXPaymentUtils {

    private WXPaymentUtils() {

    }

    /**
     * 公众号/小程序/APP统一下单
     * @return
     */
    public static Map<String, Object> unifiedOrder(WeiXinTradeTypeEnum tradeTypeEnum,String trxNo,String outTradeNo, String body, BigDecimal totalAmount, String spbillCreateIp
            , String notifyUrl, String openid, List<TransferProductDetails> transferProductDetails) {
        String nonce_str = WXCommonUtils.createNonceStr();
        Integer totalFee = totalAmount.multiply(BigDecimal.valueOf(100L)).intValue();

        SortedMap<String, Object> paramMap = new TreeMap<>();
        paramMap.put("appid", WXConfigUtils.xAppId);
        paramMap.put("mch_id", WXConfigUtils.xMchId);
        paramMap.put("device_info", nonce_str);//否
        paramMap.put("nonce_str", nonce_str);
        paramMap.put("sign_type", SecurityEnum.MD5.getDesc());//否
        paramMap.put("body", body);
        paramMap.put("attach", trxNo);
        paramMap.put("out_trade_no", outTradeNo);
        paramMap.put("total_fee", totalFee);
        paramMap.put("spbill_create_ip", spbillCreateIp);
        paramMap.put("notify_url", notifyUrl);

        if(tradeTypeEnum.name().equals(WeiXinTradeTypeEnum.JSAPI.name())){//小程序/公众号
            paramMap.put("trade_type", WeiXinTradeTypeEnum.JSAPI.name());
        }else if(tradeTypeEnum.name().equals(WeiXinTradeTypeEnum.APP.name())){//APP
            paramMap.put("trade_type", WeiXinTradeTypeEnum.APP.name());
        }

        //paramMap.put("product_id","");//否--native必传

        paramMap.put("fee_type", "CNY");//否
        paramMap.put("openid", openid);
 /*       if (!CollectionUtils.isEmpty(transferProductDetails)) {
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
        }*/
        paramMap.put("sign", WXCommonUtils.md5Sign(paramMap, WXConfigUtils.xPayKey));
        String data = mapToXml(paramMap);
        log.info("微信小程序统一下单，请求报文:{}", data);
        Map<String, Object> resultMap = httpXmlRequest(WXConfigUtils.UNIFIED_ORDER_URL, "POST", data);
        log.info("微信小程序统一下单，返回报文:{}", resultMap);
        if (resultMap == null || resultMap.isEmpty()) {
            return null;
        }
        SortedMap<String, Object> responseMap = new TreeMap<>();
        responseMap.putAll(resultMap);
        String resultSign = WXCommonUtils.getSign(responseMap, WXConfigUtils.xPayKey);
        if (resultSign.equals(resultMap.get("sign"))) {
            resultMap.put("verify", "YES");
        } else {
            log.info("返回报文验签失败,返回报文签名:{},返回签名:{}", resultSign, resultMap.get("sign"));
            resultMap.put("verify", "NO");
            resultMap.put("verify", "NO");
        }
        return resultMap;
    }


    /**
     * 订单查询
     *
     * @param outTradeNo
     * @return
     */
    public static Map<String, Object> orderQuery(String outTradeNo, String appId, String MchId) {
        SortedMap<String, Object> paramMap =  new TreeMap<>();
        paramMap.put("appid", appId);
        paramMap.put("mch_id", MchId);
        paramMap.put("nonce_str", WXCommonUtils.createNonceStr());
        paramMap.put("out_trade_no", outTradeNo);
        paramMap.put("sign", WXCommonUtils.md5Sign(paramMap,WXConfigUtils.PAY_KEY));
        String data = mapToXml(paramMap);
        Map<String, Object> resultMap = httpXmlRequest(WXConfigUtils.QUERY_ORDER_URL, "POST",
                data);
        return resultMap;
    }


    /**
     * 解析微信发来的请求（XML）
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static Map<String, String> parseXml(InputStream inputStream) throws Exception {

        if (inputStream == null) {
            return null;
        }

        Map<String, String> map = new HashMap<String, String>();// 将解析结果存储在HashMap中
        SAXReader reader = new SAXReader();// 读取输入流
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();// 得到xml根元素
        List<Element> elementList = root.elements();// 得到根元素的所有子节点
        for (Element e : elementList) { // 遍历所有子节点
            map.put(e.getName(), e.getText());
        }

        inputStream.close(); // 释放资源
        inputStream = null;

        return map;
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




    /**
     * 发送xml数据,获取返回结果
     *
     * @param requestUrl
     * @param requestMethod
     * @param xmlStr
     * @return
     */
    public static Map<String, Object> httpXmlRequest(String requestUrl, String requestMethod, String xmlStr) {
        // 将解析结果存储在HashMap中
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            HttpsURLConnection urlCon = (HttpsURLConnection) (new URL(requestUrl)).openConnection();
            urlCon.setDoInput(true);
            urlCon.setDoOutput(true);
            // 设置请求方式（GET/POST）
            urlCon.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod)) {
                urlCon.connect();
            }

            urlCon.setRequestProperty("Content-Length", String.valueOf(xmlStr.getBytes().length));
            urlCon.setUseCaches(false);
            // 设置为gbk可以解决服务器接收时读取的数据中文乱码问题
            if (null != xmlStr) {
                OutputStream outputStream = urlCon.getOutputStream();
                outputStream.write(xmlStr.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
            }
            InputStream inputStream = urlCon.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStreamReader);
            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            @SuppressWarnings("unchecked")
            List<Element> elementList = root.elements();
            // 遍历所有子节点
            for (Element e : elementList) {
                map.put(e.getName(), e.getText());
            }
            log.info("微信查询返回结果:"+map.toString());
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            urlCon.disconnect();
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return map;
    }
}
