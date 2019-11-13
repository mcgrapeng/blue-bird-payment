package com.zhangpeng.payment.core.mapper;

import com.zhangpeng.payment.center.domain.PaymentAuthorize;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface PaymentAuthorizeMapper {

    void insert(PaymentAuthorize paymentAuthorize);

    PaymentAuthorize selectBy(Map<String, Object> param);

    void deleteByUserNo(String userNo);

}
