package com.zhangpeng.payment.core.utils;

import com.alibaba.fastjson.JSON;
import com.zhangpeng.payment.center.ex.PaymentBizException;
import com.zhangpeng.payment.core.PayMDConfiguration;
import com.zhangpeng.payment.core.enums.MDPayConfigEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * 签名工具
 * 张三丰
 */
@Slf4j
public final class MDPaymentSign {

    /**
     * 获取签名
     * @param params
     * @param appSecret
     * @return
     * @throws IOException
     */
    public static String signRequest(Map<String, String> params, String appSecret) throws
            IOException {
        // 第一步：检查参数是否已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        query.append(appSecret);
        for (String key : keys) {
            if ("sign".equals(key)) {
                continue;
            }
            String value = params.get(key);
            if (StringUtils.isNoneEmpty(key, value)) {
                query.append(key).append("=").append(value).append("&");
            }
        }
        query.deleteCharAt(query.length() - 1);
        byte[] bytes;
        query.append(appSecret);
        bytes = encryptMD5(query.toString());
        // 第四步：把二进制转化为大写的十六进制
        return byte2hex(bytes);

    }

    /**
     * MD5
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] encryptMD5(String data) throws IOException {
        return DigestUtils.md5(data.getBytes("UTF-8"));
    }

    /**
     * 2进制-->16进制
     * @param bytes
     * @return
     */
    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }


    /**
     * 验证签名
     * @param param
     * @param sign
     * @return
     */
    public static boolean verifySign(Map<String,String>  param , String  payTypeCode ,String sign){
        try {
            String key = null;
            if(payTypeCode.equalsIgnoreCase(MDPayConfigEnum.MD_WX_PROGRAM_PAY.getPayType())){
                key = MDPayConfigEnum.MD_WX_PROGRAM_PAY.getKey();
            }else if(payTypeCode.equalsIgnoreCase(MDPayConfigEnum.MD_WX_PROGRAM_PAY.getPayType())){
                key = MDPayConfigEnum.MD_WX_PROGRAM_PAY.getKey();
            }
            String compareSign = MDPaymentSign.signRequest(param, key);
            if(!compareSign.equals(sign)){
                throw new PaymentBizException(PaymentBizException.TRADE_PAY_SIGN_ERROR,"签名非法");
            }
            return Boolean.TRUE;
        } catch (IOException e) {
            log.error("生成签名错误, param = {}", JSON.toJSONString(param),e);
        }
        return Boolean.FALSE;
    }
}
