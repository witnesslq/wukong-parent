package com.easemob.wukong;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Created by dongwentao on 16/10/18.
 */
public class GeneralTest {
    public static void main(String[] args) {
        BigDecimal b1 = new BigDecimal("20000");
        BigDecimal b2 = new BigDecimal("3");
        MathContext mc = new MathContext(2, RoundingMode.HALF_UP);
        System.out.println(b1.divide(b2,2,RoundingMode.HALF_UP).doubleValue());
    }
}
