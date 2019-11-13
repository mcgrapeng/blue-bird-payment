package com.zhangpeng.payment.center.enums;

import com.zhangpeng.payment.center.PayMDConfiguration;

/**
 * 接入的支付产品枚举
 * 张朋
 */
public enum PayProductEnum {

    MIAODAO_PAY (PayMDConfiguration.PRODUCT_NO,"秒到支付",Boolean.TRUE)

    ;

    private String productNo;
    private String productName;
    private boolean isGrounding;


    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public boolean isGrounding() {
        return isGrounding;
    }

    public void setGrounding(boolean grounding) {
        isGrounding = grounding;
    }

    PayProductEnum(String productNo, String productName, boolean isGrounding) {
        this.productNo = productNo;
        this.productName = productName;
        this.isGrounding = isGrounding;
    }


    public static PayProductEnum getEnum(String productNo) {
        PayProductEnum[] arry = PayProductEnum.values();
        for (int i = 0; i < arry.length; i++) {
            if (arry[i].getProductNo().equals(productNo)) {
                return arry[i];
            }
        }
        return null;
    }


}
