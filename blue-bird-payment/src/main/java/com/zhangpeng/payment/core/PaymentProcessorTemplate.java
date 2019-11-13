package com.zhangpeng.payment.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.zhangpeng.payment.center.*;
import com.zhangpeng.payment.center.domain.PaymentOrder;
import com.zhangpeng.payment.center.domain.PaymentToken;
import com.zhangpeng.payment.center.domain.PaymentWay;
import com.zhangpeng.payment.center.enums.*;
import com.zhangpeng.payment.center.ex.PaymentBizException;
import com.zhangpeng.payment.center.utils.DateUtils;
import com.zhangpeng.payment.core.utils.MDPaymentUtils;
import com.zhangpeng.payment.core.utils.PaymentSign;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 张鹏
 * 支付模板引擎
 */
@Slf4j
public abstract class PaymentProcessorTemplate implements PaymentProcessor {

    @Autowired
    protected PaymentOrderService paymentOrderService;

    @Autowired
    protected PaymentWayService paymentWayService;

    @Autowired
    protected PaymentTokenService paymentTokenService;

    @Autowired
    protected PaymentAuthorizeService paymentAuthorizeService;

    //@Autowired
    private RedisTemplate redisTemplate;

    /**
     * 封装支付流水记录实体
     *
     * @param merchantNo   商户编号
     * @param merchantName 商户名称
     * @param productName  产品名称
     * @param orderNo      商户订单号
     * @param orderPrice   订单金额
     * @param payWay       支付方式编码
     * @param payWayName   支付方式名称
     * @param feeRate      支付费率(第三方)
     * @param orderIp      订单IP
     * @param returnUrl    页面通知地址
     * @param notifyUrl    后台通知地址
     * @param remark       备注
     * @param field1       扩展字段1
     * @param field2       扩展字段2
     * @param field3       扩展字段3
     * @param field4       扩展字段4
     * @param field5       扩展字段5
     * @return
     */
    private PaymentToken sealPaymentToken(String merchantNo, String merchantName
            , String productName, String orderNo, BigDecimal orderPrice, String payWay, String payWayName
            , String payTypeCode ,String payTypeName,BigDecimal feeRate, String orderIp
            , String returnUrl, String notifyUrl, String remark
            , String field1, String field2, String field3, String field4, String field5) {

        //生成交易流水号
        String payNo = System.currentTimeMillis() + "";

        PaymentToken paymentToken = new PaymentToken();
        paymentToken.setStatus(TradeStatusEnum.WAITING_PAYMENT.name());// 订单状态// 等待支付
        paymentToken.setRemark(remark);// 支付备注
        paymentToken.setProductName(productName)// 产品名称
                .setMerchantOrderNo(orderNo)// 产品编号
                .setTrxNo(payNo)// 支付流水号
                .setMerchantName(merchantName)
                .setMerchantNo(merchantNo)// 商户编号
                .setOrderIp(orderIp)// 下单IP
                .setOrderRefererUrl("")// 下单前页面
                .setReturnUrl(returnUrl)// 页面通知地址
                .setNotifyUrl(notifyUrl)// 后台通知地址
                .setPayWayCode(payWay)// 支付通道编码
                .setPayWayName(payWayName)// 支付通道名称
                .setTrxType(TrxTypeEnum.EXPENSE.name())// 交易类型
                .setOrderFrom(OrderFromEnum.USER_EXPENSE.name())// 订单来源
                .setOrderAmount(orderPrice)// 订单金额
                .setPayTypeCode(payTypeCode)// 支付类型代码
                .setPayTypeName(payTypeName)// 支付类型名称
                .setField1(field1)// 扩展字段1
                .setField2(field2)// 扩展字段2
                .setField3(field3)// 扩展字段3
                .setField4(field4)// 扩展字段4
                .setField5(field5)// 扩展字段5
                .setFeeRate(feeRate);// 费率
        return paymentToken;
    }


    /**
     * 封装支付订单
     *
     * @return
     */
    private PaymentOrder sealPaymentOrder(PaymentREQ paymentREQ, PaymentWay paymentWay) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setStatus(TradeStatusEnum.WAITING_PAYMENT.name());// 订单状态
        paymentOrder.setRemark(paymentREQ.getRemark());// 支付备注
        paymentOrder.setProductName(paymentREQ.getProductName())// 商品名称
                .setMerchantOrderNo(paymentREQ.getMerchantOrderNo())// 订单号
                .setOrderAmount(paymentREQ.getOrderAmount())// 订单金额
                .setMerchantName(paymentREQ.getMerchantName())// 商户名称
                .setMerchantNo(paymentREQ.getMerchantNo())// 商户编号
                .setOrderDate(DateUtils.formatDate(paymentREQ.getOrderDate(), "yyyy-MM-dd"))// 下单日期
                .setOrderTime(DateUtils.formatDate(paymentREQ.getOrderTime(), "yyyy-MM-dd HH:mm:ss"))// 下单时间
                .setOrderIp(paymentREQ.getOrderIp())// 下单IP
                //.setOrderRefererUrl()// 下单前页面
                .setReturnUrl(paymentREQ.getPaySuccessUrl())// 页面通知地址
                .setNotifyUrl(paymentREQ.getNotifyUrl())// 后台通知地址
                //.setOrderPeriod(60)// 订单有效期
                // .setExpireTime(DateUtils.addMinute(paymentREQ.getOrderTime(),60))// 订单过期时间
                .setPayWayCode(paymentWay.getPayWayCode())// 支付通道编码
                .setPayWayName(paymentWay.getPayWayName())// 支付通道名称
                .setPayTypeCode(paymentWay.getPayTypeCode())// 支付类型
                .setPayTypeName(paymentWay.getPayTypeName());// 支付方式

        return paymentOrder;
    }

    //支付产品--支付方式--支付类型
    //秒到--微信--小程序支付/扫码支付

    public abstract PaymentRES process( PaymentWay payWay, PaymentREQ paymentREQ);


    /**
     * 小程序支付
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public PaymentRES pay(PaymentREQ paymentREQ) {
        //判断合作的商户存在
        if (null == MerchantEnum.getEnum(paymentREQ.getMerchantNo())) {
            return PaymentRES.of(String.valueOf(PaymentBizException.MERCHANT_IS_NOT_ERROR)
                    , "该商户尚未接入支付系统");
        }

        //查询支付产品，是否对接
        PayProductEnum payProductEnum = PayProductEnum.getEnum(paymentREQ.getProductNo());
        if (null == payProductEnum) {
            return PaymentRES.of(String.valueOf(PaymentBizException.TRADE_PAY_PRODUCT_ERROR)
                    , "该支付产品尚未接入");
        }

        if (!payProductEnum.isGrounding()) {
            return PaymentRES.of(String.valueOf(PaymentBizException.TRADE_PAY_PRODUCT_NOT_ERROR)
                    , "该支付产品已下架");
        }

        PaymentWay payWay = queryPaymentWay(payProductEnum.getProductNo(),paymentREQ.getPayType());//支付产品下的支付通道，支付通道下的支付类型
        if (payWay == null) {
            return PaymentRES.of(String.valueOf(PaymentBizException.TRADE_PAY_WAY_ERROR)
                    , "用户支付方式配置有误");
        }

        if(payWay.getStatus().equals(PublicStatusEnum.UNACTIVE.name())){
            return PaymentRES.of(String.valueOf(PaymentBizException.TRADE_PAY_WAY_ERROR)
                    , "该支付通道未激活");
        }

        //生产订单记录
        PaymentOrder paymentOrder = null;
        try {
            paymentOrder = paymentOrderService.selectByMerchantNoAndMerchantOrderNo(paymentREQ.getMerchantNo(), paymentREQ.getMerchantOrderNo());
        } catch (Exception e) {
            log.error("获取支付单发生异常，paymentREQ = {} " ,JSON.toJSONString(paymentREQ),e );
            return PaymentRES.of(String.valueOf(PaymentBizException.FAILED)
                    , "网络异常，请稍后重试");
        }
        if (paymentOrder == null) {
            paymentOrder = sealPaymentOrder(paymentREQ, payWay);
            try {
                paymentOrderService.createPaymentOrder(paymentOrder);
            } catch (Exception e) {
                log.error("创建支付单发生异常，paymentREQ = {} " ,JSON.toJSONString(paymentREQ),e );
                return PaymentRES.of(String.valueOf(PaymentBizException.FAILED)
                        , "网络异常，请稍后重试");
            }
        } else {
            if (TradeStatusEnum.SUCCESS.name().equals(paymentOrder.getStatus())) {
                return PaymentRES.of(String.valueOf(PaymentBizException.TRADE_SUCCSSED)
                        , "订单已支付成功,无需重复支付");
            }
            if (paymentOrder.getOrderAmount().compareTo(paymentREQ.getOrderAmount()) != 0) {
                paymentOrder.setOrderAmount(paymentREQ.getOrderAmount());// 如果金额不一致,修改金额为最新的金额
                try {
                    paymentOrderService.updatePaymentOrder(paymentOrder);
                } catch (Exception e) {
                    log.error("更新支付单发生异常，paymentREQ = {} " ,JSON.toJSONString(paymentREQ),e );
                    return PaymentRES.of(String.valueOf(PaymentBizException.FAILED)
                            , "网络异常，请稍后重试");
                }
            }
        }

        //生成支付流水
        PaymentToken paymentToken = sealPaymentToken(paymentOrder.getMerchantNo(), paymentOrder.getMerchantName()
                , paymentOrder.getProductName(), paymentOrder.getMerchantOrderNo(), paymentOrder.getOrderAmount()
                , payWay.getPayWayCode(), payWay.getPayWayName(), payWay.getPayTypeCode(),payWay.getPayTypeName()
                , BigDecimal.valueOf(payWay.getPayRate()), paymentOrder.getOrderIp(), paymentOrder.getReturnUrl()
                , paymentOrder.getNotifyUrl(), paymentOrder.getRemark(), paymentOrder.getField1(), paymentOrder.getField2()
                , paymentOrder.getField3(), paymentOrder.getField4(), paymentOrder.getField5());

        try {
            paymentTokenService.createPaymentToken(paymentToken);
        } catch (Exception e) {
            log.error("获取支付流水发生异常，paymentREQ = {} " ,JSON.toJSONString(paymentREQ),e );
            return PaymentRES.of(String.valueOf(PaymentBizException.FAILED)
                    , "网络异常，请稍后重试");
        }

        paymentREQ.setTradeNo(paymentToken.getTrxNo());
        return process(payWay, paymentREQ);
    }


    /**
     * 获取支付方式
     * @param payProductNo
     * @param payType
     * @return
     */
    private PaymentWay queryPaymentWay(String payProductNo,String payType){
        PayTypeEnum payTypeEnum = PayTypeEnum.getEnum(payType);/**通过支付类型name获取支付类型枚举,前端传WX_PROGRAM_PAY*/
        PaymentWay payWay = null;//支付产品下的支付通道，支付通道下的支付类型
        try {
            payWay = paymentWayService.getPaymentWay(payProductNo, payTypeEnum.getWay(), payTypeEnum.name());
        } catch (Exception e) {
            log.error("获取支付方式发生异常，payProductNo = {} , payType ={}" ,payProductNo,payType,e );
            return null;
        }
        return payWay;
    }


    /**
     * 支付成功方法
     *
     * @param paymentToken
     * @param tradeOrderNo
     * @param timeEnd
     */
    private void completeSuccessOrder(PaymentToken paymentToken
            , String tradeOrderNo, Date timeEnd) {
        log.info("订单支付成功, 入参：{}", JSON.toJSONString(paymentToken));
        paymentToken.setPaySuccessTime(timeEnd);
        paymentToken.setStatus(TradeStatusEnum.SUCCESS.name());
        paymentToken.setBankOrderNo(tradeOrderNo);
       // paymentToken.setReturnUrl(StringUtils.join(paymentToken.getReturnUrl(),tradeOrderNo));
        paymentTokenService.updatePaymentTokenStatus(paymentToken.getTrxNo(), paymentToken.getStatus());

        PaymentOrder paymentOrder = paymentOrderService.selectByMerchantNoAndMerchantOrderNo(
                paymentToken.getMerchantNo(), paymentToken.getMerchantOrderNo());
        paymentOrder.setStatus(TradeStatusEnum.SUCCESS.name());
        paymentOrder.setTrxNo(paymentToken.getTrxNo());// 设置支付平台支付流水号
       // paymentOrder.setReturnUrl(StringUtils.join( paymentOrder.getReturnUrl(),tradeOrderNo));
        paymentOrderService.updatePaymentOrderStatus(paymentOrder.getMerchantNo(), paymentOrder.getMerchantOrderNo(), paymentOrder.getStatus());
    }

    /**
     * 支付失败方法
     *
     * @param paymentToken
     */

    private void completeFailOrder(PaymentToken paymentToken) {
        paymentToken.setStatus(TradeStatusEnum.FAILED.name());
        paymentTokenService.updatePaymentTokenStatus(paymentToken.getTrxNo(), paymentToken.getStatus());

        PaymentOrder paymentOrder = paymentOrderService.selectByMerchantNoAndMerchantOrderNo(paymentToken.getMerchantNo()
                , paymentToken.getMerchantOrderNo());
        paymentOrder.setStatus(TradeStatusEnum.FAILED.name());
        paymentOrderService.updatePaymentOrderStatus(paymentOrder.getMerchantNo(), paymentOrder.getMerchantOrderNo(), paymentOrder.getStatus());
    }


    /**
     * 支付通知
     *
     * @param tradeNo         第三方支付机构流水号
     * @param tradeOrderNo    第三方支付机构订单号
     * @param trxNo           支付系统的支付流水号
     * @param merchantOrderNo 商户平台订单号
     * @param merchantNo      商户号
     * @param orderAmount
     * @param payStatus
     * @param payFinishTime
     * @param sign            支付机构签名
     * @param resultCode
     */
    @Transactional
    @Override
    public PaymentRES completePay(String payProductNo,String payTypeCode,String tradeNo, String tradeOrderNo, String trxNo, String merchantOrderNo, String merchantNo
            , String orderAmount, String payStatus, String payFinishTime, String sign, String resultCode) {

        // 根据交易流水号获取支付信息
        PaymentToken paymentToken = null;
        try {
            paymentToken = paymentTokenService.queryPaymentTokenByTrxNo(trxNo);
        } catch (Exception e) {
            log.error("获取支付流水发生异常，tradeOrderNo = {} , trxNo = {} , orderNo = {}" ,tradeOrderNo,trxNo,merchantOrderNo,e );
            return PaymentRES.of(String.valueOf(PaymentBizException.FAILED)
                    , "网络异常，请稍后重试");
        }
        if (paymentToken == null) {
            PaymentWay payWay = queryPaymentWay(payProductNo,payTypeCode);//支付产品下的支付通道，支付通道下的支付类型
            if (payWay == null) {
                return PaymentRES.of(String.valueOf(PaymentBizException.TRADE_PAY_WAY_ERROR)
                        , "用户支付方式配置有误");
            }
            if(payWay.getPayWayCode().equals(PayWayEnum.MIAODAO.name())){
                //查询秒到支付订单
                JSONObject jsonObject = MDPaymentUtils.queryOrder(merchantOrderNo);
                if(null != jsonObject
                        &&  jsonObject.getBoolean("result")  && jsonObject.getInteger("code") == 200){
                    Object data = jsonObject.get("data");
                    if(null == data){
                        return PaymentRES.of(String.valueOf(PaymentBizException.TRADE_PAY_NO_ERROR),"非法交易流水,交易流水号不存在");
                    }
                }
            }
        }

        if (TradeStatusEnum.SUCCESS.name().equals(paymentToken.getStatus())) {
            return PaymentRES.of(String.valueOf(PaymentBizException.TRADE_SUCCSSED),"订单已交易成功");
        }

        if (null == MerchantEnum.getEnum(merchantNo)) {
            return PaymentRES.of(String.valueOf(PaymentBizException.MERCHANT_IS_NOT_ERROR),"商户号非法");
        }

        if (!orderAmount.equals(paymentToken.getOrderAmount().toPlainString())) {
            return PaymentRES.of(String.valueOf(PaymentBizException.TRADE_ORDER_AMOUNT_ERROR),"订单金额不一致");
        }

        if (!merchantOrderNo.equals(paymentToken.getMerchantOrderNo())) {
            return PaymentRES.of(String.valueOf(PaymentBizException.TRADE_ORDER_NO_ERROR),"订单号不一致");
        }

        Map<String, String> param = Maps.newHashMap();
        param.put("tradeNo", tradeOrderNo);
        param.put("outTradeNo", merchantOrderNo);
        param.put("payStatus", payStatus);
        param.put("amount", orderAmount);

        if (PaymentSign.verifySign(param, sign)) {
            if (TradeStatusEnum.SUCCESS.name().equalsIgnoreCase(resultCode)) {// 业务结果
                // 成功
                Date timeEnd = null;
                if (!StringUtils.isEmpty(payFinishTime)) {
                    timeEnd = DateUtils.getDateFromString(payFinishTime, "yyyy-MM-dd HH:mm:ss");// 订单支付完成时间
                }
                try {
                    completeSuccessOrder(paymentToken, tradeOrderNo, timeEnd);
                } catch (Exception e) {
                    log.error("完成支付单SUCCESS发生异常，tradeOrderNo = {} , trxNo = {} , orderNo = {}" ,tradeOrderNo,trxNo,merchantOrderNo,e );
                    return PaymentRES.of(String.valueOf(PaymentBizException.FAILED)
                            , "网络异常，请稍后重试");
                }
                return PaymentRES.of(String.valueOf(PaymentBizException.SUCCESS),"success");
            } else {
                try {
                    completeFailOrder(paymentToken);
                } catch (Exception e) {
                    log.error("完成支付单FAILED发生异常，tradeOrderNo = {} , trxNo = {} , orderNo = {}" ,tradeOrderNo,trxNo,merchantOrderNo,e );
                    return PaymentRES.of(String.valueOf(PaymentBizException.FAILED)
                            , "网络异常，请稍后重试");
                }
            }
        }
        return PaymentRES.of(String.valueOf(PaymentBizException.FAILED),"网络异常，请稍后重试");
    }


    /**
     * 支付成功后
     * 可能会出现页面通知早与后台通知 现页面通知,暂时不做数据处理功能,只生成页面通知URL
     * @param merchantNo
     * @param merchantOrderNo 商户订单号
     * @return
     */
    @Transactional
    @Override
    public PaymentRES completePayResult(String payProductNo,String payTypeCode,String  merchantNo ,String merchantOrderNo) {
        // 根据银行订单号获取支付信息
        PaymentToken paymentToken = null;
        try {
            paymentToken = paymentTokenService.queryPaymentToken(merchantNo,merchantOrderNo);
        } catch (Exception e) {
            log.error("获取支付流水发生异常，orderNo = {}" ,merchantOrderNo,e );
            return PaymentRES.of(String.valueOf(PaymentBizException.FAILED)
                    , "网络异常，请稍后重试");
        }

        if (paymentToken == null) {
            PaymentWay payWay = queryPaymentWay(payProductNo,payTypeCode);//支付产品下的支付通道，支付通道下的支付类型
            if (payWay == null) {
                return PaymentRES.of(String.valueOf(PaymentBizException.TRADE_PAY_WAY_ERROR)
                        , "用户支付方式配置有误");
            }
            if(payWay.getPayWayCode().equals(PayWayEnum.MIAODAO.name())){
                //查询秒到支付订单
                JSONObject jsonObject = MDPaymentUtils.queryOrder(merchantOrderNo);
                if(null != jsonObject
                        &&  jsonObject.getBoolean("result")  && jsonObject.getInteger("code") == 200){
                    Object ret = jsonObject.get("data");
                    if(null == ret){
                        return PaymentRES.of(String.valueOf(PaymentBizException.TRADE_PAY_NO_ERROR),"非法交易流水,交易流水号不存在");
                    }
                }
            }
        }

        Map<String, Object> data = Maps.newHashMap();
        data.put("orderNo", paymentToken.getOrderAmount());//订单号
        data.put("orderAmount", paymentToken.getOrderAmount());// 订单金额
        data.put("productName", paymentToken.getProductName());// 产品名称
//        data.put("returnUrl",paymentToken.getReturnUrl());

        PaymentOrder paymentOrder = null;
        try {
            paymentOrder = paymentOrderService.selectByMerchantNoAndMerchantOrderNo(paymentToken.getMerchantNo()
                    , paymentToken.getMerchantOrderNo());
        } catch (Exception e) {
           log.error("获取支付单发生异常，orderNo = {}" ,merchantOrderNo,e );
            return PaymentRES.of(String.valueOf(PaymentBizException.FAILED)
                    , "网络异常，请稍后重试");
        }

        if (paymentOrder == null) {
            PaymentWay payWay = queryPaymentWay(payProductNo,payTypeCode);//支付产品下的支付通道，支付通道下的支付类型
            if (payWay == null) {
                return PaymentRES.of(String.valueOf(PaymentBizException.TRADE_PAY_WAY_ERROR)
                        , "用户支付方式配置有误");
            }
            if(payWay.getPayWayCode().equals(PayWayEnum.MIAODAO.name())){
                //查询秒到支付订单
                JSONObject jsonObject = MDPaymentUtils.queryOrder(merchantOrderNo);
                if(null != jsonObject
                        &&  jsonObject.getBoolean("result")  && jsonObject.getInteger("code") == 200){
                    Object ret = jsonObject.get("data");
                    if(null == ret){
                        return PaymentRES.of(String.valueOf(PaymentBizException.TRADE_ORDER_NO_ERROR)
                                , "非法订单,订单不存在");
                    }
                }
            }
        }

        TradeStatusEnum tradeStatusEnum = TradeStatusEnum.getEnum(paymentOrder.getStatus());
        data.put("payStatus", tradeStatusEnum.name());
        return PaymentRES.of(String.valueOf(PaymentBizException.SUCCESS), data, "success");
    }



    /**
     * 先这么搞
     * @return
     */
//    private  String getTrxNoByTime() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//        String newDate = sdf.format(new Date());
//        String result = "";
//        Random random = new Random();
//        for (int i = 0; i < 3; i++) {
//            result += random.nextInt(10);
//        }
//        return newDate + result;
//    }
}
