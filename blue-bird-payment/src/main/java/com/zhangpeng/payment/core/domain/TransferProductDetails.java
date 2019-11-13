package com.zhangpeng.payment.core.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@Data
public class TransferProductDetails implements Serializable {

    private String  goodsId;
    private String  goodsName;
    private Integer quantity;
    private Integer price;


}
