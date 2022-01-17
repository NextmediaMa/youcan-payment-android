package com.youcanPay.networking;

import com.youcanPay.api.YCPayApiProvider;
import com.youcanPay.exception.YCPayInvalidArgumentException;
import com.youcanPay.models.HttpResponse;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.youcanPay.config.YCPayConfig.API_URL;
import static com.youcanPay.config.YCPayConfig.API_URL_SANDBOX;

public class YCPayRetrofitHTTPAdapter extends YCPayHTTPAdapter implements Callback<ResponseBody> {
    private final YCPayApiProvider apiProviderPay;

    public YCPayRetrofitHTTPAdapter() {
        apiProviderPay = getClient().create(YCPayApiProvider.class);
    }

    private Retrofit getClient() {
        return new Retrofit.Builder()
                .baseUrl(this.isSandboxMode ? API_URL_SANDBOX : API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void request(
            String method,
            String endpoint,
            HashMap<String, String> params,
            HashMap<String, String> headers
    ) throws YCPayInvalidArgumentException {
        switch (method.toLowerCase()) {
            case "post":
                apiProviderPay.post(endpoint, params, headers).enqueue(this);
                break;

            case "get":
                apiProviderPay.get(endpoint, headers).enqueue(this);
                break;

            default:
                throw new YCPayInvalidArgumentException("Invalid request method");
        }
    }

    @Override
    public void onResponse(
            @NotNull Call call,
            @NotNull Response response
    ) {
        HttpResponse result = new HttpResponse();

        try {
            result.setSuccess(response.isSuccessful());

            if (response.isSuccessful()) {
                result.setStatusCode(response.code());
                result.setBody(((ResponseBody) response.body()).string());
                httpCallback.onResponse(result);

                return;
            }

            result.setStatusCode(response.code());
            result.setBody(response.errorBody().string());

            this.httpCallback.onResponse(result);
        } catch (IOException e) {
            e.printStackTrace();
            this.httpCallback.onError(e.getMessage());
        }
    }

    @Override
    public void onFailure(
            @NotNull Call call,
            Throwable t
    ) {
        httpCallback.onError(t.getMessage());
    }
}
