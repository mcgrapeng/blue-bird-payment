package com.zhangpeng.payment.center.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class HttpUtils {

    /**
     * post请求
     *
     * @param url
     * @param json
     * @return
     */
    public static JSONObject doPost(String url, Map<String,String> json) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            // StringEntity s = new StringEntity(json.toString());
            // s.setContentEncoding("UTF-8");
            // s.setContentType("application/x-www-form-urlencoded;charset=UTF-8");//发送表单数据需要设置contentType
            // post.setEntity(s);

            List<BasicNameValuePair> pairList = new ArrayList<>();
            String totalAmount = json.get("totalAmount");
            if(StringUtils.isNotBlank(totalAmount)){
                pairList.add(new BasicNameValuePair("totalAmount", totalAmount));
            }
            String productName = json.get("productName");
            if(StringUtils.isNotBlank(productName)){
                pairList.add(new BasicNameValuePair("productName", productName));
            }
            String merchantNo = json.get("merchantNo");
            if(StringUtils.isNotBlank(merchantNo)){
                pairList.add(new BasicNameValuePair("merchantNo", merchantNo));
            }
            String outTradeNo = json.get("outTradeNo");
            if(StringUtils.isNotBlank(outTradeNo)){
                pairList.add(new BasicNameValuePair("outTradeNo", outTradeNo));
            }
            String appId = json.get("appId");
            if(StringUtils.isNotBlank(appId)){
                pairList.add(new BasicNameValuePair("appId", appId));
            }
            String openId = json.get("openId");
            if(StringUtils.isNotBlank(openId)){
                pairList.add(new BasicNameValuePair("openId", openId));
            }
            String piType = json.get("piType");
            if(StringUtils.isNotBlank(piType)){
                pairList.add(new BasicNameValuePair("piType", piType));
            }
            String gatewayPayMethod = json.get("gatewayPayMethod");
            if(StringUtils.isNotBlank(gatewayPayMethod)){
                pairList.add(new BasicNameValuePair("gatewayPayMethod", gatewayPayMethod));
            }

            String agencyCode = json.get("agencyCode");
            if(StringUtils.isNotBlank(agencyCode)){
                pairList.add(new BasicNameValuePair("agencyCode", agencyCode));
            }
            String notifyUrl = json.get("notifyUrl");
            if(StringUtils.isNotBlank(notifyUrl)){
                pairList.add(new BasicNameValuePair("notifyUrl", notifyUrl));
            }

            String successPageUrl = json.get("successPageUrl");
            if(StringUtils.isNotBlank(successPageUrl)){
                pairList.add(new BasicNameValuePair("successPageUrl", successPageUrl));
            }

            String sign = json.get("sign");
            pairList.add(new BasicNameValuePair("sign", sign));
            post.setEntity(new UrlEncodedFormEntity(pairList, "utf-8"));

            HttpResponse res = httpclient.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(res.getEntity());// 返回json格式：
                if (StringUtils.isNotBlank(result)) {
                    response = JSONObject.parseObject(result);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }



    /**
     * post请求
     *
     * @param url
     * @param json
     * @return
     */
    public static JSONObject doGet(String url, Map<String,String> json) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            // StringEntity s = new StringEntity(json.toString());
            // s.setContentEncoding("UTF-8");
            // s.setContentType("application/x-www-form-urlencoded;charset=UTF-8");//发送表单数据需要设置contentType
            // post.setEntity(s);

            List<BasicNameValuePair> pairList = new ArrayList<>();
            post.setEntity(new UrlEncodedFormEntity(pairList, "utf-8"));
            HttpResponse res = httpclient.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(res.getEntity());// 返回json格式：
                if (StringUtils.isNotBlank(result)) {
                    response = JSONObject.parseObject(result);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
