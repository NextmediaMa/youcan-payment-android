package com.canshipy.youcanpaymentandroidsdk.config;

public class Config {

    public static String API_URL = "https://payment.testyoucan.shop/api/";
    public static String URL_TOKENIZATION;
    public static String URL_PAY;

    public static String CURRENCY = "MAD";
    public static String ORDER_ID = "111";
    public static String ACCOUNT_ID = "24499aee-e76d-42d0-8d55-f62e37b6a6d3";


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
