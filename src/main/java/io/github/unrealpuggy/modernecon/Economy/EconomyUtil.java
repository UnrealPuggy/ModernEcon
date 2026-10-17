package io.github.unrealpuggy.modernecon.Economy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EconomyUtil {
    private static final Map<Character, Double> MULTIPLIERS = Map.of(
            'k', 1e3,
            'm', 1e6,
            'b', 1e9,
            't', 1e12
    );

    public static double roundCurrency(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) return 0;

        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }


    private static final Pattern CLEAN_PATTERN = Pattern.compile("^([0-9]+(?:\\.[0-9]+)?)([kmbt])?");

    public static long parseToCents(String input) throws IllegalArgumentException {
        if (input == null || input.isBlank()) return 0L;
        String clean = input.trim().toLowerCase();
        Matcher matcher = CLEAN_PATTERN.matcher(clean);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid price format: " + input);
        }
        try {
            double dollars = Double.parseDouble(matcher.group(1));
            double multiplier = 1.0;

            String suffixStr = matcher.group(2);
            if (suffixStr != null && !suffixStr.isEmpty()) {
                char suffix = suffixStr.charAt(0);
                multiplier = MULTIPLIERS.getOrDefault(suffix, 1.0);
            }
            dollars *= multiplier;
            return Math.round((dollars) * 100.0);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid price format: " + input);
        }
    }

    ;

    public static String formatCents(long cents) {
        double dollars = cents / 100.0;
        double absDollars = Math.abs(dollars);
        if (absDollars >= 1e12) return formatCompact(dollars, 1e12, "t");
        if (absDollars >= 1e9) return formatCompact(dollars, 1e9, "b");
        if (absDollars >= 1e6) return formatCompact(dollars, 1e6, "m");
        if (absDollars >= 1e3) return formatCompact(dollars, 1e3, "k");

        return formatCompact(dollars,1,"");//String.format("%.2f",dollars);
    }

    private static String formatCompact(double dollars, double divisor, String suffix) {
        double value = dollars / divisor;
        if (value == (long) value) {
            return (long) value + suffix;
        }
        return String.format("%.2f", value) + suffix;
    }


    public static long doubleToCents(double value) {
        return Math.round(value * 100);
    }

//    public static double centsToDouble(long cents) {
//        return
//    }
}
