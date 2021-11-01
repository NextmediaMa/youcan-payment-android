package com.youcanPay.api;

import com.youcanPay.models.YCPayResponse;

import java.util.HashMap;

import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiProviderPay {
    @Headers("Accept: application/json")
    @POST("api/pay")
    Call<YCPayResponse> pay(@Body HashMap<String, String> body, @Header("X-Preferred-Locale") String locale);
}
