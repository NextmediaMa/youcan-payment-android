package com.youcanPay.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.youcanPay.config.YCPayConfig.API_URL;

public class Services {

    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
