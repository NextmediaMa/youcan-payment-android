package com.youcanPay.services;

import android.content.Context;

import com.youcanPay.api.ApiProviderPay;
import com.youcanPay.interfaces.PayCallbackImpl;
import com.youcanPay.models.YCPayCardInformation;
import com.youcanPay.models.YCPayResult;
import com.youcanPay.utils.Strings;
import com.youcanPay.view.YCPayBottomDialog;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.youcanPay.config.YCPayConfig.API_URL;
import static com.youcanPay.config.YCPayConfig.API_URL_SANDBOX;

public class ApiService implements Callback<YCPayResult> {
    private PayCallbackImpl payCallback;
    private final Context context;
    private final String locale;
    private final boolean isSandboxMode;
    private final ApiProviderPay apiProvider;

    public ApiService(Context context, boolean isSandboxMode, String locale) {
        this.context = context;
        this.isSandboxMode = isSandboxMode;
        this.apiProvider = getClient().create(ApiProviderPay.class);
        this.locale = locale;
    }

    private Retrofit getClient() {
        return new Retrofit.Builder()
                .baseUrl(this.isSandboxMode ? API_URL_SANDBOX : API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void pay(YCPayCardInformation cardInformation, PayCallbackImpl payCallBack, String pubKey, String tokenId) {
        HashMap<String, String> body = cardInformation.toHashMap();
        body.put("token_id", tokenId);
        body.put("pub_key", pubKey);
        body.put("is_mobile", "1");

        this.payCallback = payCallBack;

        Call<YCPayResult> call = this.apiProvider.pay(body, locale);

        call.enqueue(this);
    }

    private void onPaySuccessful(YCPayResult result, PayCallbackImpl payCallBack) {
        if (!result.redirectUrl.equals("") && !result.returnUrl.equals("")) {
            show3DsSheet(this.context, result);

            return;
        }

        if (result.success) {
            payCallBack.onSuccess(result.transactionId);

            return;

        }

        payCallBack.onFailure(Strings.get("unexpected_error_occurred", this.locale));
    }

    private void onPayResponseError(Response<YCPayResult> response, PayCallbackImpl payCallBack) {

        String errorBody = "";

        try {
            errorBody = response.errorBody().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        YCPayResult result = new YCPayResult().resultFromJson(errorBody);

        payCallBack.onFailure(result.message);
    }

    @Override
    public void onResponse(Call<YCPayResult> call, Response<YCPayResult> response) {
        YCPayResult result;

        if (response.isSuccessful()) {
            result = response.body();
            onPaySuccessful(result, payCallback);

            return;
        }

        onPayResponseError(response, payCallback);
    }

    @Override
    public void onFailure(Call<YCPayResult> call, Throwable t) {
        payCallback.onFailure(Strings.get("unexpected_error_occurred", this.locale));
    }

    private void show3DsSheet(Context context, YCPayResult ycPayResult) {
        YCPayBottomDialog bottomDialog = new YCPayBottomDialog.Builder(context)
                .setData(ycPayResult, this.payCallback, this.locale)
                .build();

        bottomDialog.show();
    }

}
