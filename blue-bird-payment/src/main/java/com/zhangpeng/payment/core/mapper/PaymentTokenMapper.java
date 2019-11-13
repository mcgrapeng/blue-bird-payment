package com.zhangpeng.payment.core.mapper;

import com.zhangpeng.payment.center.domain.PaymentToken;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;
@Mapper
public interface PaymentTokenMapper {

    PaymentToken selectBy(Map<String, Object> params);

    void insert(PaymentToken paymentToken);

    void update(PaymentToken paymentToken);
}
