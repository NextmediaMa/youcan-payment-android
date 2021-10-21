package com.youcanPay.api;

import com.youcanPay.models.YCPayResult;

import java.util.HashMap;

import retrofit2.Call;

import retrofit2.http.Body;

import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiProvider {

    @Headers("Accept: application/json")
    @POST("api/pay")
    Call<YCPayResult> pay(@Body HashMap<String, String> body);
}
