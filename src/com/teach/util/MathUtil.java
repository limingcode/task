package com.teach.util;

import java.math.BigDecimal;

/**
 * @author huangRunDong
 * @createTime 10:26 2019/9/2
 */
public class MathUtil {
    /**
     *  保留小数
     * @param f
     * @param decimalPlaces
     * @return
     */
    public static Double keepDecimal (double f,int decimalPlaces){
        BigDecimal b   =   new   BigDecimal(f);
        return  b.setScale(decimalPlaces,   BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
