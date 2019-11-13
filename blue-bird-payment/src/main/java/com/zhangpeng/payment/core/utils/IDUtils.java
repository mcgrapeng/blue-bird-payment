package com.zhangpeng.payment.core.utils;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Calendar;
import java.util.Date;

public final class IDUtils {


    /**
     * 获取年的后两位加上一年多少天+当前小时数作为前缀
     * @param date
     * @return
     */
    private static String getOrderIdPrefix(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        //补两位,因为一年最多三位数
        String monthFormat = String.format("%1$02d", month+1);
        //补两位，因为日最多两位数
        String dayFormat = String.format("%1$02d", day);
        //补两位，因为小时最多两位数
        String hourFormat = String.format("%1$02d", hour);
        return year + monthFormat + dayFormat+hourFormat;
    }

    /**
     * 生成订单号
     * @return
     */
    public static String orderNo(Date date, RedisTemplate redisTemplate) {
        String prefix = getOrderIdPrefix(date);
        String key = "KKD_ORDER_NO_" + prefix;
        String orderId = null;
        try {
            Long increment = redisTemplate.opsForValue().increment(key,1);
            //往前补6位
            orderId=prefix+String.format("%1$06d",increment);
        } catch (Exception e) {
            System.out.println("生成订单号失败");
            e.printStackTrace();
        }
        return String.valueOf(orderId);
    }


    /**
     * 生成流水号
     * @return
     */
    public static String trxNo(Date date,RedisTemplate redisTemplate) {
        String prefix = getOrderIdPrefix(date);
        String key = "KKD_TRX_NO_" + prefix;
        String orderId = null;
        try {
            Long increment = redisTemplate.opsForValue().increment(key,1);
            //往前补6位
            orderId=prefix+String.format("%1$06d",increment);
        } catch (Exception e) {
            System.out.println("生成流水号号失败");
            e.printStackTrace();
        }
        return String.valueOf(orderId);
    }

}
