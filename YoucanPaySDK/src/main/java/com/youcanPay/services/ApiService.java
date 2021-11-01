package com.youcanPay.services;

import android.content.Context;

import com.youcanPay.api.ApiProviderPay;
import com.youcanPay.interfaces.PayCallbackImpl;
import com.youcanPay.models.YCPayCardInformation;
import com.youcanPay.models.YCPayResponse;
import com.youcanPay.utils.Strings;
import com.youcanPay.view.YCPayBottomSheet;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.youcanPay.config.YCPayConfig.API_URL;
import static com.youcanPay.config.YCPayConfig.API_URL_SANDBOX;
import static com.youcanPay.config.YCPayConfig.locale;

public class ApiService implements Callback<YCPayResponse> {
    private final PayCallbackImpl payCallback;
    private final Context context;
    private final boolean isSandboxMode;
    private final ApiProviderPay apiProvider;
    private final YCPayCardInformation cardInformation;
    private final String pubKey;
    private final String tokenId;

    public ApiService(
            Context context,
            boolean isSandboxMode,
            YCPayCardInformation cardInformation,
            PayCallbackImpl payCallback,
            String pubKey,
            String tokenId
    ) {
        this.context = context;
        this.isSandboxMode = isSandboxMode;
        this.cardInformation = cardInformation;
        this.payCallback = payCallback;
        this.pubKey = pubKey;
        this.tokenId = tokenId;
        this.apiProvider = getClient().create(ApiProviderPay.class);
    }

    private Retrofit getClient() {
        return new Retrofit.Builder()
                .baseUrl(this.isSandboxMode ? API_URL_SANDBOX : API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void pay() throws Exception {
        if (this.payCallback == null) {
            throw new Exception("Null Exception: Pay Callback is null");
        }

        if (this.cardInformation == null) {
            throw new Exception("Null Exception: cardInformation is null");
        }

        HashMap<String, String> body = this.cardInformation.toHashMap();
        body.put("token_id", this.tokenId);
        body.put("pub_key", this.pubKey);
        body.put("is_mobile", "1");

        Call<YCPayResponse> call = this.apiProvider.pay(body, locale);

        call.enqueue(this);
    }

    private void onPaySuccessful(YCPayResponse result) {
        if (!result.redirectUrl.equals("") && !result.returnUrl.equals("")) {
            show3DsSheet(this.context, result);

            return;
        }

        if (result.success) {
            this.payCallback.onSuccess(result.transactionId);

            return;

        }

        this.payCallback.onFailure(Strings.get("unexpected_error_occurred", locale));
    }

    private void onPayResponseError(Response<YCPayResponse> response) {
        String errorBody = "";

        try {
            errorBody = response.errorBody().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        YCPayResponse result = null;
        try {
            result = new YCPayResponse().resultFromJson(errorBody);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.payCallback.onFailure(result.message);
    }

    @Override
    public void onResponse(@NotNull Call<YCPayResponse> call, Response<YCPayResponse> response) {
        YCPayResponse result;

        if (response.isSuccessful()) {
            result = response.body();
            onPaySuccessful(result);

            return;
        }

        onPayResponseError(response);
    }

    @Override
    public void onFailure(@NotNull Call<YCPayResponse> call, @NotNull Throwable t) {
        payCallback.onFailure(Strings.get("unexpected_error_occurred", locale));
    }

    private void show3DsSheet(Context context, YCPayResponse ycPayResult) {
        YCPayBottomSheet bottomDialog = new YCPayBottomSheet(context, ycPayResult, this.payCallback);
        bottomDialog.show();
    }

}
