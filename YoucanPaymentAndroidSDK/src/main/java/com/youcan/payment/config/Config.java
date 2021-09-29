package com.youcan.payment.config;

public class Config {

    public static String API_URL = "https://pay.testyoucan.shop/api/";
    public static String API_URL_TEST = "https://pay.testyoucan.shop/api/";
    public static String API_URL_PROD = "https://pay.youcan.shop/api/";
    public static String URL_TOKENIZATION;
    public static String URL_PAY;

    public static String CURRENCY = "MAD";
    public static String ORDER_ID = "111";
    public static String ACCOUNT_ID = "24499aee-e76d-42d0-8d55-f62e37b6a6d3";
    public static String PUB_KEY = "pub_7d6d21fc-9077-41be-853e-0f2e72d0";


    static {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(API_URL);
        localStringBuilder.append("tokenize");
        URL_TOKENIZATION = localStringBuilder.toString();
    }

    static {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(API_URL);
        localStringBuilder.append("pay");
        URL_PAY = localStringBuilder.toString();
    }
    
}
