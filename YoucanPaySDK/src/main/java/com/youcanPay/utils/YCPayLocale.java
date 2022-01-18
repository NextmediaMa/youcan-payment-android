package com.youcanPay.utils;

import java.util.Arrays;
import java.util.List;

public class YCPayLocale {
    private static final String defaultLocale = "en";
    private static String locale = defaultLocale;
    private static final List<String> supportedLang = Arrays.asList("en", "ar", "fr");

    public static String getDefaultLocale() {
        return defaultLocale;
    }

    public static String getLocale() {
        return locale;
    }

    public static void setLocale(String currentLocale) {
        if (supportedLang.contains(currentLocale)) {
            locale = currentLocale;

            return;
        }
        locale = defaultLocale;
    }
}
