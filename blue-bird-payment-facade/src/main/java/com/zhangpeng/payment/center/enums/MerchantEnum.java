package com.zhangpeng.payment.center.enums;

import com.zhangpeng.payment.center.PayMDConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合作系统（商户）
 * 张朋
 */
public enum MerchantEnum {

    KKD(PayMDConfiguration.MERCHANT_NO,"卡卡得"),

    KKD_TEST("13235880088","卡卡得测试");

    private String code;
    /** 描述 */
    private String desc;


    MerchantEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static Map<String, Map<String, Object>> toMap() {
        MerchantEnum[] ary = MerchantEnum.values();
        Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
        for (int num = 0; num < ary.length; num++) {
            Map<String, Object> map = new HashMap<String, Object>();
            String key = ary[num].name();
            map.put("desc", ary[num].getDesc());
            enumMap.put(key, map);
        }
        return enumMap;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List toList() {
        MerchantEnum[] ary = MerchantEnum.values();
        List list = new ArrayList();
        for (int i = 0; i < ary.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("desc", ary[i].getDesc());
            list.add(map);
        }
        return list;
    }

    public static MerchantEnum getEnum(String code) {
        MerchantEnum[] arry = MerchantEnum.values();
        for (int i = 0; i < arry.length; i++) {
            if (arry[i].getCode().equals(code)) {
                return arry[i];
            }
        }
        return null;
    }


    /**
     * 取枚举的json字符串
     *
     * @return
     */
    public static String getJsonStr() {
        MerchantEnum[] enums = MerchantEnum.values();
        StringBuffer jsonStr = new StringBuffer("[");
        for (MerchantEnum senum : enums) {
            if (!"[".equals(jsonStr.toString())) {
                jsonStr.append(",");
            }
            jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("'}");
        }
        jsonStr.append("]");
        return jsonStr.toString();
    }
}
