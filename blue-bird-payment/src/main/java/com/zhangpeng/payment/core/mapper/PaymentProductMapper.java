package com.zhangpeng.payment.core.mapper;

import com.zhangpeng.payment.center.domain.PaymentProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;
@Mapper
public interface PaymentProductMapper {

    PaymentProduct selectBy(Map<String, Object> params);
}
