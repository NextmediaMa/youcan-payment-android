package com.youcanPay.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.youcanPay.config.YCPayConfig.API_URL;

public class ApiServices {

    private static Retrofit retrofit;
    private static Retrofit retrofitPaymentCallBack;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public static Retrofit getClientPaymentCallBack(String url) {
        if (retrofitPaymentCallBack == null) {
            retrofitPaymentCallBack = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofitPaymentCallBack;
    }

}
