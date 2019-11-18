package com.zhangpeng.payment.center;

/**
 * @desc   秒到支付参数配置
 * @author 张三丰
 */
public final class PayMDConfiguration {

    //商户号 18616216339  秘钥88d2e2069f8fbb6130e7d5d72e61a519  机构号86038810   --- 小程序
    //商户号 18616216341 秘钥aa403187f550527bf7cda6affb0660ab 机构号86038810  --公众号

    /**商户号*/
    public final static String  MERCHANT_NO = "18616216341";

    /**密钥*/
    public final static String  KEY = "aa403187f550527bf7cda6affb0660ab";

    /**机构号*/
    public final static String  ORGAN_NO = "86038810";

    /**支付产品号*/
    public final static String PRODUCT_NO = "35";

    /**支付方式*/
    public final static String PAY_WAY = "WX";

    /**接入方式*/
    public final static String ACCESS_MODE = "SUBSCRIPTION";


    /**appid*/
    public final static String  APP_ID = "wxd678efh567hg6787";



    public final static String ORDER_URL = "http://open.miaodaochina.com/unify/mini/pay";
    public final static String OPENID_URL = "http://open.miaodaochina.com/unify/query-openid";
    public final static String ORDER_QUERY_URL = "http://open.miaodaochina.com/unify/query-order";

    public final static String CLOSE_ORDER_URL = "http://open.miaodaochina.com/unify/order-close";
}
