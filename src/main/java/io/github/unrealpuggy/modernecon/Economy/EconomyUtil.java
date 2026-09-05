package io.github.unrealpuggy.modernecon.Economy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EconomyUtil {

    public static double roundCurrency(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) return 0;

        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
