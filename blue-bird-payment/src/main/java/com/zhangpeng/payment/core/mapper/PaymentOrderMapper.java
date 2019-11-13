package com.zhangpeng.payment.core.mapper;

import com.zhangpeng.payment.center.domain.PaymentOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;
@Mapper
public interface PaymentOrderMapper {

    PaymentOrder selectBy(Map<String, Object> param);

    void insert(PaymentOrder paymentOrder);

    void update(PaymentOrder paymentOrder);
}
