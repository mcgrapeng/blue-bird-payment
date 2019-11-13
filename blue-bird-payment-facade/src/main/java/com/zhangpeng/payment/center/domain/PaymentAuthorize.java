package com.zhangpeng.payment.center.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentAuthorize  implements Serializable {

    private Integer id;

    private String userNo;

    private String openId;

}
