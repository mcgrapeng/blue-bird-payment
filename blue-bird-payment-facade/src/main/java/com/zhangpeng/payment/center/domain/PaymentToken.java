package com.zhangpeng.payment.center.domain;



import com.zhangpeng.payment.center.enums.PayTypeEnum;
import com.zhangpeng.payment.center.enums.PayWayEnum;
import com.zhangpeng.payment.center.enums.TradeStatusEnum;
import com.zhangpeng.payment.center.enums.TrxTypeEnum;
import com.zhangpeng.payment.center.utils.DateUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PaymentToken extends BaseEntity implements Serializable {

    /**
     * 商品名称
     **/
    private String productName;

    /**
     * 商户订单号
     **/
    private String merchantOrderNo;

    /**
     * 支付流水号
     **/
    private String trxNo;

    /**
     * 银行订单号
     **/
    private String bankOrderNo;

    /**
     * 银行流水号
     **/
    private String bankTrxNo;

    /**
     * 商户名称
     **/
    private String merchantName;

    /**
     * 商户编号
     **/
    private String merchantNo;

    /**
     * 付款方编号
     **/
    private String payerUserNo;

    /**
     * 付款方名称
     **/
    private String payerName;

    /**
     * 付款方支付金额
     **/
    private BigDecimal payerPayAmount;

    /**
     * 付款方手续费
     **/
    private BigDecimal payerFee;

    /**
     * 付款方账户类型
     **/
    private String payerAccountType;

    /**
     * 收款方编号
     **/
    private String receiverUserNo;

    /**
     * 收款方名称
     **/
    private String receiverName;

    /**
     * 收款方收款金额
     **/
    private BigDecimal receiverPayAmount;

    /**
     * 收款方手续费
     **/
    private BigDecimal receiverFee;

    /**
     * 收款方账户类型
     **/
    private String receiverAccountType;

    /**
     * 下单IP
     **/
    private String orderIp;

    /**
     * 页面链接
     **/
    private String orderRefererUrl;

    /**
     * 订单金额
     **/
    private BigDecimal orderAmount;

    /**
     * 平台收入 初始创建默认为-
     **/
    private BigDecimal platIncome = BigDecimal.ZERO;

    /**
     * 费率
     **/
    private BigDecimal feeRate = BigDecimal.ZERO;

    /**
     * 平台成本
     **/
    private BigDecimal platCost = BigDecimal.ZERO;

    /**
     * 平台利润
     **/
    private BigDecimal platProfit = BigDecimal.ZERO;

    /**
     * 支付结果页面通知地址
     **/
    private String returnUrl;

    /**
     * 支付结果后台通知地址
     **/
    private String notifyUrl;

    /**
     * 支付通道编码
     **/
    private String payWayCode;

    /**
     * 支付通道名称
     **/
    private String payWayName;

    /**
     * 成功支付时间
     **/
    private Date paySuccessTime;

    /**
     * 完成时间
     **/
    private Date completeTime;

    /**
     * 是否退款
     **/
    private boolean isRefund = Boolean.FALSE;

    /**
     * 退款次数
     **/
    private Integer refundTimes;

    /**
     * 成功退款金额
     **/
    private BigDecimal successRefundAmount;

    /**
     * 业务类型
     **/
    private String trxType;

    /**
     * 订单来源
     **/
    private String orderFrom;

    /**
     * 支付方式类型编码
     **/
    private String payTypeCode;

    /**
     * 支付方式类型名称
     **/
    private String payTypeName;

    /**
     * 资金流入类型
     **/
    private String fundIntoType;

    /**
     * 银行返回信息
     **/
    private String bankReturnMsg;

    /**
     * 扩展字段1
     **/
    private String field1;

    /**
     * 扩展字段2
     **/
    private String field2;

    /**
     * 扩展字段3
     **/
    private String field3;

    /**
     * 扩展字段4
     **/
    private String field4;

    /**
     * 扩展字段5
     **/
    private String field5;

    public String getProductName() {
        return productName;
    }

    public PaymentToken setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
        return this;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public PaymentToken setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo == null ? null : merchantOrderNo.trim();
        return this;
    }

    public String getTrxNo() {
        return trxNo;
    }

    public PaymentToken setTrxNo(String trxNo) {
        this.trxNo = trxNo == null ? null : trxNo.trim();
        return this;
    }

    public String getBankOrderNo() {
        return bankOrderNo;
    }

    public PaymentToken setBankOrderNo(String bankOrderNo) {
        this.bankOrderNo = bankOrderNo == null ? null : bankOrderNo.trim();
        return this;
    }

    public String getBankTrxNo() {
        return bankTrxNo;
    }

    public PaymentToken setBankTrxNo(String bankTrxNo) {
        this.bankTrxNo = bankTrxNo == null ? null : bankTrxNo.trim();
        return this;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public PaymentToken setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
        return this;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public PaymentToken setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo == null ? null : merchantNo.trim();
        return this;
    }

    public String getPayerUserNo() {
        return payerUserNo;
    }

    public PaymentToken setPayerUserNo(String payerUserNo) {
        this.payerUserNo = payerUserNo == null ? null : payerUserNo.trim();
        return this;
    }

    public String getPayerName() {
        return payerName;
    }

    public PaymentToken setPayerName(String payerName) {
        this.payerName = payerName == null ? null : payerName.trim();
        return this;
    }

    public BigDecimal getPayerPayAmount() {
        return payerPayAmount;
    }

    public PaymentToken setPayerPayAmount(BigDecimal payerPayAmount) {
        this.payerPayAmount = payerPayAmount;
        return this;
    }

    public BigDecimal getPayerFee() {
        return payerFee;
    }

    public PaymentToken setPayerFee(BigDecimal payerFee) {
        this.payerFee = payerFee;
        return this;
    }

    public String getPayerAccountType() {
        return payerAccountType;
    }

    public PaymentToken setPayerAccountType(String payerAccountType) {
        this.payerAccountType = payerAccountType == null ? null : payerAccountType.trim();
        return this;
    }

    public String getReceiverUserNo() {
        return receiverUserNo;
    }

    public PaymentToken setReceiverUserNo(String receiverUserNo) {
        this.receiverUserNo = receiverUserNo == null ? null : receiverUserNo.trim();
        return this;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public PaymentToken setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
        return this;
    }

    public BigDecimal getReceiverPayAmount() {
        return receiverPayAmount;
    }

    public PaymentToken setReceiverPayAmount(BigDecimal receiverPayAmount) {
        this.receiverPayAmount = receiverPayAmount;
        return this;
    }

    public BigDecimal getReceiverFee() {
        return receiverFee;
    }

    public PaymentToken setReceiverFee(BigDecimal receiverFee) {
        this.receiverFee = receiverFee;
        return this;
    }

    public String getReceiverAccountType() {
        return receiverAccountType;
    }

    public PaymentToken setReceiverAccountType(String receiverAccountType) {
        this.receiverAccountType = receiverAccountType == null ? null : receiverAccountType.trim();
        return this;
    }

    public String getOrderIp() {
        return orderIp;
    }

    public PaymentToken setOrderIp(String orderIp) {
        this.orderIp = orderIp == null ? null : orderIp.trim();
        return this;
    }

    public String getOrderRefererUrl() {
        return orderRefererUrl;
    }

    public PaymentToken setOrderRefererUrl(String orderRefererUrl) {
        this.orderRefererUrl = orderRefererUrl == null ? null : orderRefererUrl.trim();
        return this;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public PaymentToken setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
        return this;
    }

    public BigDecimal getPlatIncome() {
        return platIncome;
    }

    public PaymentToken setPlatIncome(BigDecimal platIncome) {
        this.platIncome = platIncome;
        return this;
    }

    public BigDecimal getFeeRate() {
        return feeRate;
    }

    public PaymentToken setFeeRate(BigDecimal feeRate) {
        this.feeRate = feeRate;
        return this;
    }

    public BigDecimal getPlatCost() {
        return platCost;
    }

    public PaymentToken setPlatCost(BigDecimal platCost) {
        this.platCost = platCost;
        return this;
    }

    public BigDecimal getPlatProfit() {
        return platProfit;
    }

    public PaymentToken setPlatProfit(BigDecimal platProfit) {
        this.platProfit = platProfit;
        return this;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public PaymentToken setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl == null ? null : returnUrl.trim();
        return this;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public PaymentToken setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl == null ? null : notifyUrl.trim();
        return this;
    }

    public String getPayWayCode() {
        return payWayCode;
    }

    public PaymentToken setPayWayCode(String payWayCode) {
        this.payWayCode = payWayCode == null ? null : payWayCode.trim();
        return this;
    }

    public String getPayWayName() {
        return payWayName;
    }

    public PaymentToken setPayWayName(String payWayName) {
        this.payWayName = payWayName == null ? null : payWayName.trim();
        return this;
    }

    public Date getPaySuccessTime() {
        return paySuccessTime;
    }

    public PaymentToken setPaySuccessTime(Date paySuccessTime) {
        this.paySuccessTime = paySuccessTime;
        return this;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public PaymentToken setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
        return this;
    }


    public boolean isRefund() {
        return isRefund;
    }

    public PaymentToken setRefund(boolean refund) {
        isRefund = refund;
        return this;
    }

    public BigDecimal getSuccessRefundAmount() {
        return successRefundAmount;
    }

    public PaymentToken setSuccessRefundAmount(BigDecimal successRefundAmount) {
        this.successRefundAmount = successRefundAmount;
        return this;
    }


    public String getOrderFrom() {
        return orderFrom;
    }

    public PaymentToken setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom == null ? null : orderFrom.trim();
        return this;
    }

    public String getPayTypeCode() {
        return payTypeCode;
    }

    public PaymentToken setPayTypeCode(String payTypeCode) {
        this.payTypeCode = payTypeCode == null ? null : payTypeCode.trim();
        return this;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public PaymentToken setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName == null ? null : payTypeName.trim();
        return this;
    }

    public String getFundIntoType() {
        return fundIntoType;
    }

    public PaymentToken setFundIntoType(String fundIntoType) {
        this.fundIntoType = fundIntoType == null ? null : fundIntoType.trim();
        return this;
    }


    public String getField1() {
        return field1;
    }

    public PaymentToken setField1(String field1) {
        this.field1 = field1 == null ? null : field1.trim();
        return this;
    }

    public String getField2() {
        return field2;
    }

    public PaymentToken setField2(String field2) {
        this.field2 = field2 == null ? null : field2.trim();
        return this;
    }

    public String getField3() {
        return field3;
    }

    public PaymentToken setField3(String field3) {
        this.field3 = field3 == null ? null : field3.trim();
        return this;
    }

    public String getField4() {
        return field4;
    }

    public PaymentToken setField4(String field4) {
        this.field4 = field4 == null ? null : field4.trim();
        return this;
    }

    public String getField5() {
        return field5;
    }

    public PaymentToken setField5(String field5) {
        this.field5 = field5 == null ? null : field5.trim();
        return this;
    }

    public Integer getRefundTimes() {
        return refundTimes;
    }

    public PaymentToken setRefundTimes(Integer refundTimes) {
        this.refundTimes = refundTimes;
        return this;
    }

    public String getTrxType() {
        return trxType;
    }

    public PaymentToken setTrxType(String trxType) {
        this.trxType = trxType;
        return this;
    }

    public String getBankReturnMsg() {
        return bankReturnMsg;
    }

    public PaymentToken setBankReturnMsg(String bankReturnMsg) {
        this.bankReturnMsg = bankReturnMsg;
        return this;
    }

    public String getTrxTypeDesc() {
        TrxTypeEnum trxTypeEnum = TrxTypeEnum.getEnum(this.getTrxType());
        return null != trxTypeEnum ? trxTypeEnum.getDesc() : null;
    }

    public String getPayWayNameDesc() {
        PayWayEnum anEnum = PayWayEnum.getEnum(this.getPayWayCode());
        PayTypeEnum anEnum1 = PayTypeEnum.getEnum(this.getPayTypeCode());
        if (anEnum != null && anEnum1 != null) {
            return anEnum.getDesc() + "-" + anEnum1.getDesc();
        }
        return null;
    }

    public String getStatusDesc() {
        return TradeStatusEnum.getEnum(this.getStatus()).getDesc();
    }

    public String getCreateTimeDesc() {
        return DateUtils.formatDate(this.getCreateTime(), "yyyy-MM-dd HH:mm");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", createTime=").append(super.getCreateTime());
        sb.append(", status=").append(super.getStatus());
        sb.append(", editor=").append(super.getEditor());
        sb.append(", creater=").append(super.getCreater());
        sb.append(", editTime=").append(super.getEditTime());
        sb.append(", productName=").append(productName);
        sb.append(", merchantOrderNo=").append(merchantOrderNo);
        sb.append(", trxNo=").append(trxNo);
        sb.append(", bankOrderNo=").append(bankOrderNo);
        sb.append(", bankTrxNo=").append(bankTrxNo);
        sb.append(", merchantName=").append(merchantName);
        sb.append(", merchantNo=").append(merchantNo);
        sb.append(", payerUserNo=").append(payerUserNo);
        sb.append(", payerName=").append(payerName);
        sb.append(", payerPayAmount=").append(payerPayAmount);
        sb.append(", payerFee=").append(payerFee);
        sb.append(", payerAccountType=").append(payerAccountType);
        sb.append(", receiverUserNo=").append(receiverUserNo);
        sb.append(", receiverName=").append(receiverName);
        sb.append(", receiverPayAmount=").append(receiverPayAmount);
        sb.append(", receiverFee=").append(receiverFee);
        sb.append(", receiverAccountType=").append(receiverAccountType);
        sb.append(", orderIp=").append(orderIp);
        sb.append(", orderRefererUrl=").append(orderRefererUrl);
        sb.append(", orderAmount=").append(orderAmount);
        sb.append(", platIncome=").append(platIncome);
        sb.append(", feeRate=").append(feeRate);
        sb.append(", platCost=").append(platCost);
        sb.append(", platProfit=").append(platProfit);
        sb.append(", returnUrl=").append(returnUrl);
        sb.append(", notifyUrl=").append(notifyUrl);
        sb.append(", payWayCode=").append(payWayCode);
        sb.append(", payWayName=").append(payWayName);
        sb.append(", paySuccessTime=").append(paySuccessTime);
        sb.append(", completeTime=").append(completeTime);
        sb.append(", isRefund=").append(isRefund);
        sb.append(", refundTimes=").append(refundTimes);
        sb.append(", successRefundAmount=").append(successRefundAmount);
        sb.append(", trxType=").append(trxType);
        sb.append(", orderFrom=").append(orderFrom);
        sb.append(", payTypeCode=").append(payTypeCode);
        sb.append(", payTypeName=").append(payTypeName);
        sb.append(", fundIntoType=").append(fundIntoType);
        sb.append(", bankReturnMsg=").append(bankReturnMsg);
        sb.append(", field1=").append(field1);
        sb.append(", field2=").append(field2);
        sb.append(", field3=").append(field3);
        sb.append(", field4=").append(field4);
        sb.append(", field5=").append(field5);
        sb.append("]");
        return sb.toString();
    }
}
