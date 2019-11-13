package com.zhangpeng.payment.center.ex;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PaymentBizException extends BizException{

    private static final long serialVersionUID = 3536909333010163563L;

    /** 错误的支付方式 **/
    public static final int TRADE_PAY_WAY_ERROR = 102;
    /** 微信异常 **/
    public static final int TRADE_WEIXIN_ERROR = 103;
    /** 订单异常 **/
    public static final int TRADE_ORDER_ERROR = 104;
    /** 交易记录状态不为成功 **/
    public static final int TRADE_ORDER_STATUS_NOT_SUCCESS = 105;
    /** 支付宝异常 **/
    public static final int TRADE_ALIPAY_ERROR = 106;
    /** 参数异常 **/
    public static final int TRADE_PARAM_ERROR = 107;
    /**商户不存在*/
    public static final int MERCHANT_IS_NOT_ERROR = 108;
    /** 错误的支付产品 **/
    public static final int TRADE_PAY_PRODUCT_ERROR = 109;
    /**支付产品已下架*/
    public static final int TRADE_PAY_PRODUCT_NOT_ERROR = 110;
    /**签名错误*/
    public static final int TRADE_PAY_SIGN_ERROR = 111;

    /**订单金额异常*/
    public static final int TRADE_ORDER_AMOUNT_ERROR = 112;

    /**订单号异常*/
    public static final int TRADE_ORDER_NO_ERROR = 113;

    /**交易流水号异常*/
    public static final int TRADE_PAY_NO_ERROR = 114;

    /**已交易成功*/
    public static final int TRADE_SUCCSSED = 100;


    public static final int SUCCESS = 200;

    public static final int FAILED  = 500;


    public PaymentBizException() {
    }

    public PaymentBizException(int code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
    }

    public PaymentBizException(int code, String msg) {
        super(code, msg);
    }

    public PaymentBizException print() {
        log.info("==>BizException, code:" + this.code + ", msg:" + this.msg);
        return this;
    }
}
