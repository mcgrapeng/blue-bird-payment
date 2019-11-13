package com.zhangpeng.payment.center;

/**
 * @desc   秒到支付参数配置
 * @author 张三丰
 */
public final class PayMDConfiguration {

    /**商户号*/
    public final static String  MERCHANT_NO = "18616216340";

    /**密钥*/
    public final static String  KEY = "88d2e2069f8fbb6130e7d5d72e61a519";

    /**机构号*/
    public final static String  ORGAN_NO = "86038810";

    /**支付产品号*/
    public final static String PRODUCT_NO = "35";

    /**支付方式*/
    public final static String PAY_WAY = "WX";

    /**接入方式*/
    public final static String ACCESS_MODE = "MINIPROGRAM";


    /**appid（生产环境：KADAO）*/
    public final static String  APP_ID = "wxa3da6581820b3143";

    /**openid*/
   // public final static String OPEN_ID = "og0bL5jKwLRK3qdNmH_2PJufTSBw";

    public final static String ORDER_URL = "http://open.miaodaochina.com/unify/mini/pay";
    public final static String OPENID_URL = "http://open.miaodaochina.com/unify/query-openid";
    public final static String ORDER_QUERY_URL = "http://open.miaodaochina.com/unify/query-order";
}
