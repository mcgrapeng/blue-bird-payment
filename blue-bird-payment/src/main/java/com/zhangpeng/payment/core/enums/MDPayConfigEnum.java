package com.zhangpeng.payment.core.enums;

import com.zhangpeng.payment.center.PayMDConfiguration;

/**
 * 秒到支付配置参数
 */
public enum  MDPayConfigEnum {

    MD_WX_ACCOUNT_PAY("WX","18616216341","aa403187f550527bf7cda6affb0660ab","wx925e07c16ab2eb6c","SUBSCRIPTION", PayMDConfiguration.ORGAN_NO,"微信公众号支付"),
    MD_WX_PROGRAM_PAY("WX","18616216340","025d10134fd3f986b84657b9b3bf9f7a","wxa3da6581820b3143","MINIPROGRAM",PayMDConfiguration.ORGAN_NO,"微信小程序支付"),
    ;

    private String payType;

    private String merchantNo;

    private String key;

    private String appId;

    private String  accessMode;

    private String organNo;

    /** 描述 */
    private String desc;

    public String getPayType() {
        return payType;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAppId() {
        return appId;
    }

    public String getAccessMode() {
        return accessMode;
    }


    MDPayConfigEnum(String payType, String merchantNo, String key, String appId, String accessMode, String organNo, String desc) {
        this.payType = payType;
        this.merchantNo = merchantNo;
        this.key = key;
        this.appId = appId;
        this.accessMode = accessMode;
        this.organNo = organNo;
        this.desc = desc;
    }
}
