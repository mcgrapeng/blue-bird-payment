package com.zhangpeng.payment.center.domain;

import com.zhangpeng.payment.center.enums.PublicEnum;

import java.io.Serializable;

/**
 * 支付产品实体
 * 张朋
 */
public class PaymentProduct extends BaseEntity implements Serializable {

    /**支付产品编号*/
    private String productCode;

    /**支付产品名称*/
    private String productName;

    /**审核状态*/
    private String auditStatus;


    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public String getAuditStatusDesc() {
        return PublicEnum.getEnum(this.getAuditStatus()).getDesc();
    }
}