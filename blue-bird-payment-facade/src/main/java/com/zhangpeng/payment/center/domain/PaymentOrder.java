package com.zhangpeng.payment.center.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付订单
 */
public class PaymentOrder extends  BaseEntity implements Serializable {

    /** 商品名称 **/
    private String productName;

    /** 商户订单编号(如：卡道的订单) **/
    private String merchantOrderNo;

    /** 订单金额 **/
    private BigDecimal orderAmount;

    /** 订单来源 **/
    private String orderFrom;

    /** 商户名称 **/
    private String merchantName;

    /** 商户编号 **/
    private String merchantNo;

    /** 订单时间 **/
    private String orderTime;

    /** 订单日期 **/
    private String orderDate;

    /** 订单来源IP **/
    private String orderIp;

    /** 页面链接 **/
    private String orderRefererUrl;

    /** 页面回调通知地址 **/
    private String returnUrl;

    /** 后台异步通知地址 **/
    private String notifyUrl;

    /** 订单撤销原因 **/
    private String cancelReason;

    /** 订单有效期(分钟) **/
    private Integer orderPeriod;

    /** 订单到期时间 **/
    private Date expireTime;

    /** 支付通道编号 **/
    private String payWayCode;

    /** 支付方式名称 **/
    private String payWayName;

    /** 交易业务类型 **/
    private String trxType;

    /** 支付流水 **/
    private String trxNo;

    /** 支付方式类型编码 **/
    private String payTypeCode;

    /** 支付方式类型名称 **/
    private String payTypeName;

    /** 资金流入类型 **/
    private String fundIntoType;

    /** 是否退款 **/
    private boolean isRefund =Boolean.FALSE;

    /** 退款次数 **/
    private Short refundTimes;

    /** 成功退款金额 **/
    private BigDecimal successRefundAmount;

    /** 扩展字段1 **/
    private String field1;

    /** 扩展字段2 **/
    private String field2;

    /** 扩展字段3 **/
    private String field3;

    /** 扩展字段4 **/
    private String field4;

    /** 扩展字段5 **/
    private String field5;

    public String getProductName() {
        return productName;
    }

    public PaymentOrder setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
        return this;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public PaymentOrder setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo == null ? null : merchantOrderNo.trim();
        return this;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public PaymentOrder setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
        return this;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public PaymentOrder setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom == null ? null : orderFrom.trim();
        return this;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public PaymentOrder setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
        return this;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public PaymentOrder setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo == null ? null : merchantNo.trim();
        return this;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public PaymentOrder setOrderTime(String orderTime) {
        this.orderTime = orderTime;
        return this;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public PaymentOrder setOrderDate(String orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public String getOrderIp() {
        return orderIp;
    }

    public PaymentOrder setOrderIp(String orderIp) {
        this.orderIp = orderIp == null ? null : orderIp.trim();
        return this;
    }

    public String getOrderRefererUrl() {
        return orderRefererUrl;
    }

    public PaymentOrder setOrderRefererUrl(String orderRefererUrl) {
        this.orderRefererUrl = orderRefererUrl == null ? null : orderRefererUrl.trim();
        return this;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public PaymentOrder setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl == null ? null : returnUrl.trim();
        return this;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public PaymentOrder setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl == null ? null : notifyUrl.trim();
        return this;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public PaymentOrder setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason == null ? null : cancelReason.trim();
        return this;
    }

    public Integer getOrderPeriod() {
        return orderPeriod;
    }

    public PaymentOrder setOrderPeriod(Integer orderPeriod) {
        this.orderPeriod = orderPeriod;
        return this;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public PaymentOrder setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
        return this;
    }

    public String getPayWayCode() {
        return payWayCode;
    }

    public PaymentOrder setPayWayCode(String payWayCode) {
        this.payWayCode = payWayCode == null ? null : payWayCode.trim();
        return this;
    }

    public String getPayWayName() {
        return payWayName;
    }

    public PaymentOrder setPayWayName(String payWayName) {
        this.payWayName = payWayName == null ? null : payWayName.trim();
        return this;
    }

    public String getTrxType() {
        return trxType;
    }

    public PaymentOrder setTrxType(String trxType) {
        this.trxType = trxType == null ? null : trxType.trim();
        return this;
    }

    public String getPayTypeCode() {
        return payTypeCode;
    }

    public PaymentOrder setPayTypeCode(String payTypeCode) {
        this.payTypeCode = payTypeCode == null ? null : payTypeCode.trim();
        return this;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public PaymentOrder setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName == null ? null : payTypeName.trim();
        return this;
    }

    public String getFundIntoType() {
        return fundIntoType;
    }

    public PaymentOrder setFundIntoType(String fundIntoType) {
        this.fundIntoType = fundIntoType == null ? null : fundIntoType.trim();
        return this;
    }

    public boolean isRefund() {
        return isRefund;
    }

    public PaymentOrder setRefund(boolean refund) {
        isRefund = refund;
        return this;
    }

    public Short getRefundTimes() {
        return refundTimes;
    }

    public PaymentOrder setRefundTimes(Short refundTimes) {
        this.refundTimes = refundTimes;
        return this;
    }

    public BigDecimal getSuccessRefundAmount() {
        return successRefundAmount;
    }

    public PaymentOrder setSuccessRefundAmount(BigDecimal successRefundAmount) {
        this.successRefundAmount = successRefundAmount;
        return this;
    }

    public String getTrxNo() {
        return trxNo;
    }

    public PaymentOrder setTrxNo(String trxNo) {
        this.trxNo = trxNo;
        return this;
    }

    public String getField1() {
        return field1;
    }

    public PaymentOrder setField1(String field1) {
        this.field1 = field1 == null ? null : field1.trim();
        return this;
    }

    public String getField2() {
        return field2;
    }

    public PaymentOrder setField2(String field2) {
        this.field2 = field2 == null ? null : field2.trim();
        return this;
    }

    public String getField3() {
        return field3;
    }

    public PaymentOrder setField3(String field3) {
        this.field3 = field3 == null ? null : field3.trim();
        return this;
    }

    public String getField4() {
        return field4;
    }

    public PaymentOrder setField4(String field4) {
        this.field4 = field4 == null ? null : field4.trim();
        return this;
    }

    public String getField5() {
        return field5;
    }

    public PaymentOrder setField5(String field5) {
        this.field5 = field5 == null ? null : field5.trim();
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", createTime=").append(super.getCreateTime());
        sb.append(", editor=").append(super.getEditor());
        sb.append(", creater=").append(super.getCreater());
        sb.append(", editTime=").append(super.getEditTime());
        sb.append(", status=").append(super.getStatus());
        sb.append(", productName=").append(productName);
        sb.append(", merchantOrderNo=").append(merchantOrderNo);
        sb.append(", orderAmount=").append(orderAmount);
        sb.append(", orderFrom=").append(orderFrom);
        sb.append(", merchantName=").append(merchantName);
        sb.append(", merchantNo=").append(merchantNo);
        sb.append(", orderTime=").append(orderTime);
        sb.append(", orderDate=").append(orderDate);
        sb.append(", orderIp=").append(orderIp);
        sb.append(", orderRefererUrl=").append(orderRefererUrl);
        sb.append(", returnUrl=").append(returnUrl);
        sb.append(", notifyUrl=").append(notifyUrl);
        sb.append(", cancelReason=").append(cancelReason);
        sb.append(", orderPeriod=").append(orderPeriod);
        sb.append(", expireTime=").append(expireTime);
        sb.append(", payWayCode=").append(payWayCode);
        sb.append(", payWayName=").append(payWayName);
        sb.append(", trxType=").append(trxType);
        sb.append(", payTypeCode=").append(payTypeCode);
        sb.append(", payTypeName=").append(payTypeName);
        sb.append(", fundIntoType=").append(fundIntoType);
        sb.append(", isRefund=").append(isRefund);
        sb.append(", refundTimes=").append(refundTimes);
        sb.append(", successRefundAmount=").append(successRefundAmount);
        sb.append(", trxNo=").append(trxNo);
        sb.append(", field1=").append(field1);
        sb.append(", field2=").append(field2);
        sb.append(", field3=").append(field3);
        sb.append(", field4=").append(field4);
        sb.append(", field5=").append(field5);
        sb.append("]");
        return sb.toString();
    }
}
