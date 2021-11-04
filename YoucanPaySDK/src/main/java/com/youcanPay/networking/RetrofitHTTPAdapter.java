package com.youcanPay.networking;

import com.youcanPay.api.ApiProvider;
import com.youcanPay.factories.YCPResponseFactory;
import com.youcanPay.interfaces.HttpCallbackImpl;
import com.youcanPay.models.YCPayResponse;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.youcanPay.config.YCPayConfig.API_URL;
import static com.youcanPay.config.YCPayConfig.API_URL_SANDBOX;

public class RetrofitHTTPAdapter extends HTTPAdapter implements Callback<ResponseBody> {
    private final ApiProvider apiProviderPay;
    private final HttpCallbackImpl httpCallback;

    public RetrofitHTTPAdapter(boolean isSandboxMode, HttpCallbackImpl httpCallback) {
        super(isSandboxMode);
        apiProviderPay = getClient().create(ApiProvider.class);
        this.httpCallback = httpCallback;
    }

    private Retrofit getClient() {
        return new Retrofit.Builder()
                .baseUrl(this.isSandboxMode ? API_URL_SANDBOX : API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void request(String method, String endpoint, HashMap<String, String> params,
                        HashMap<String, String> headers) throws Exception {
        switch (method.toLowerCase()) {
            case "post":
                apiProviderPay.post(endpoint, params, headers).enqueue(this);
                break;

            case "get":
                apiProviderPay.get(endpoint, params, headers).enqueue(this);
                break;

            default:
                throw new Exception("Invalid request method");
        }
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) {
        YCPayResponse result;
        try {
            if (response.isSuccessful()) {
                result = YCPResponseFactory.getResponse(((ResponseBody) response.body()).string());
                httpCallback.onResponse(result);

                return;
            }

            result = YCPResponseFactory.getResponse(response.errorBody().string());
            this.httpCallback.onResponse(result);
        } catch (Exception e) {
            e.printStackTrace();
            this.httpCallback.onError(e.getMessage());
        }
    }

    @Override
    public void onFailure(@NotNull Call call, Throwable t) {
        httpCallback.onError(t.getMessage());
    }
}
