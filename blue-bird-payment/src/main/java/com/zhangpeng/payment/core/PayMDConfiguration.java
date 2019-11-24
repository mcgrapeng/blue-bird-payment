package com.zhangpeng.payment.core;

/**
 * @desc   秒到支付参数配置
 * @author 张三丰
 */
public final class PayMDConfiguration {

    //商户号 18616216340   秘钥025d10134fd3f986b84657b9b3bf9f7a  机构号86038810   --- 小程序
    //商户号 18616216341   秘钥aa403187f550527bf7cda6affb0660ab 机构号86038810  --公众号


    /**机构号*/
    public final static String  ORGAN_NO = "8416950664151";

    /**支付产品号*/
    public final static String PRODUCT_NO = "01";


    public final static String ORDER_URL = "http://open.miaodaochina.com/unify/mini/pay";
    public final static String OPENID_URL = "http://open.miaodaochina.com/unify/query-openid";
    public final static String ORDER_QUERY_URL = "http://open.miaodaochina.com/unify/query-order";
    public final static String CLOSE_ORDER_URL = "http://open.miaodaochina.com/unify/order-close";
}
