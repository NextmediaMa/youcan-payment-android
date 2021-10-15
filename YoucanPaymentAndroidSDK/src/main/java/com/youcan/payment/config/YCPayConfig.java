package com.youcan.payment.config;

public class YCPayConfig {

    public static String API_URL = "https://pay.youcan.shop/api/";
    public static String URL_PAY;

    static {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(API_URL);
        localStringBuilder.append("pay");
        URL_PAY = localStringBuilder.toString();
    }
    
}
