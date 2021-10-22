package com.youcanPay.api;

import com.youcanPay.models.YCPayBalanceResult;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiProviderPaymentCallBack {
    @Headers("Accept: application/json")
    @POST(".")
    Call<YCPayBalanceResult> callPaymentCall(@HeaderMap HashMap<String, String> headers, @Body HashMap<String, String> body);
}
