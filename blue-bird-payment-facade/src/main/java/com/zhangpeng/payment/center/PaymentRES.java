package com.zhangpeng.payment.center;

import java.io.Serializable;

public class PaymentRES<T> implements Serializable {

    private String code;
    private T data;
    private String message;

    public PaymentRES(String code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message= message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PaymentRES(String code, String message) {
        this(code,null,message);
    }

    public static <T> PaymentRES<T> of(String code, T data, String message){
        return new PaymentRES<T>(code,data,message);
    }

    public static <T> PaymentRES<T> of(String code, String message){
        return new PaymentRES<T>(code,message);
    }

}
