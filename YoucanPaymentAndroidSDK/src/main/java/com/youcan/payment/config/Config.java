package com.youcan.payment.config;

public class Config {

    public static String API_URL = "https://pay.youcan.shop/api/";
    public static String URL_PAY;

    public static String CURRENCY = "MAD";

    static {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(API_URL);
        localStringBuilder.append("pay");
        URL_PAY = localStringBuilder.toString();
    }
    
}
