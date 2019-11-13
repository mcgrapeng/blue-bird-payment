package com.zhangpeng.payment.core.mapper;

import com.zhangpeng.payment.center.domain.PaymentWay;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;
@Mapper
public interface PaymentWayMapper {

    PaymentWay selectBy(Map<String, Object> params);
}
