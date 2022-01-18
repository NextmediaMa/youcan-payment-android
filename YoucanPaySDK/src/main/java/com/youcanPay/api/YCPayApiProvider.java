package com.youcanPay.api;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface YCPayApiProvider {
    @POST
    Call<ResponseBody> post(
            @Url String endpoint,
            @Body HashMap<String, String> params,
            @HeaderMap HashMap<String, String> header
    );

    @GET
    Call<ResponseBody> get(
            @Url String endpoint,
            @HeaderMap HashMap<String, String> header
    );
}
