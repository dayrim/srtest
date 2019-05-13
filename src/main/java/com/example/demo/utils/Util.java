package com.example.demo.utils;

import org.hibernate.internal.util.SerializationHelper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The Class Util.
 */
public class Util {
    private static SimpleDateFormat formatMonthYear = new SimpleDateFormat("MMM yy");

    /**
     * Checks if is null.
     *
     * @param obj the obj
     * @return true, if is null
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isEmpty(String str) {
        return str.length()==0;
    }

    public static String toNiceString(Object o) {
        return isNull(o) ? "empty" : o.toString();
    }

    /**
     * Checks if is not null.
     *
     * @param obj the obj
     * @return true, if is not null
     */
    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    public static boolean equals(Object o1, Object o2) {
        return o1 == o2 || !(isNull(o1) || isNull(o2)) && o1.equals(o2);
    }


    public static boolean isTrue(Boolean tst) {
        return Util.isNotNull(tst) && tst;
    }

    public static boolean isFalse(Boolean tst) {
        return Util.isNotNull(tst) && !tst;
    }


    public static String formatMonthYear(Date value) {
        if (isNull(value)) {
            return null;
        }
        return formatMonthYear.format(value);
    }

    public static Date parseMonthYear(String value) {
        return parseDate(value, formatMonthYear);
    }

    public static Date parseDate(String value, DateFormat formatter) {
        if (Nulls.nte(value).isEmpty()) {
            return null;
        }
        try {
            return formatter.parse(value);
        } catch (Exception e) {
            throw new RuntimeException("Date in wrong format.");
        }
    }

    public static int compareInteger(Integer v1, Integer v2) {
        int x = Util.isNull(v1) ? Integer.MAX_VALUE : v1;
        int y = Util.isNull(v2) ? Integer.MAX_VALUE : v2;

        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }

    @SuppressWarnings("unchecked")
    public static <T> T cloneObject(Class<T> clazz, T dtls) {
        return (T) SerializationHelper.clone((Serializable) dtls);
    }

    public static float getPercentage(float value, float base, int scale) {
        if (base == 0) {
            return 0;
        }
        return Util.round(value * (100 / base), scale);
    }

    public static float round(float value, int scale) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(scale, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    public static Calendar setTimeToBeginningOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

}
