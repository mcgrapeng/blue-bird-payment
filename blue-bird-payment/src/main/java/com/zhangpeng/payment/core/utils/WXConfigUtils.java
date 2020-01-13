package com.zhangpeng.payment.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

/**
 * <b>功能说明:微信属性文件工具类
 * </b>
 */
@Slf4j
public class WXConfigUtils {
    /**
     * 通过静态代码块读取上传文件的验证格式配置文件,静态代码块只执行一次(单例)
     */
    private static Properties properties = new Properties();

    public static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public static final String QUERY_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";

    public static final String PAY_KEY = "efc19b528d3c4defd84ca511bf6a5749";

    private WXConfigUtils() {

    }

    // 通过类装载器装载进来
    static {
        try {
            // 从类路径下读取属性文件
            properties.load(WXConfigUtils.class.getClassLoader()
                    .getResourceAsStream("props/wxpay_config.properties"));
        } catch (IOException e) {
            log.error("微信支付配置文件加载发生异常",e);
        }
    }

    /**
     * 函数功能说明 ：读取配置项 Administrator 2012-12-14 修改者名字 ： 修改日期 ： 修改内容 ：
     *
     * @参数：
     * @return void
     * @throws
     */
    public static String readConfig(String key) {
        return (String) properties.get(key);
    }

    //app_id
    public static final String appId = (String) properties.get("appId");

    //商户号
    public static final String mch_id = (String) properties.get("mch_id");

    //商户秘钥
    public static final String partnerKey = (String) properties.get("partnerKey");

    //小程序支付
    public static final String xAuthUrl = (String) properties.get("x_auth_url");
    public static final String xGrantType = (String) properties.get("x_grant_type");
    public static final String xAppId = (String) properties.get("x_appId");
    public static final String xPartnerKey = (String) properties.get("x_partnerKey");
    public static final String xPayKey = (String) properties.get("x_payKey");
    public static final String xMchId = (String) properties.get("x_mch_id");
    public static final String x_notify_url = (String) properties.get("x_notify_url");
}
