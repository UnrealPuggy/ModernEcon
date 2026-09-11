package io.github.unrealpuggy.modernecon.Economy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EconomyUtil {

    public static double roundCurrency(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) return 0;

        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
    public static String centsToString(long cents) {
        if(cents % 100 == 0) {
            return cents/100 + "";
        }
        return cents/100 + "." + cents % 100;
    };

    public static long doubleToCents(double value) {
        return Math.round(value * 100);
    }

//    public static double centsToDouble(long cents) {
//        return
//    }
}
