package com.youcanPay.viewModel;

import android.util.Log;

import com.youcanPay.api.ApiProviderPaymentCallBack;
import com.youcanPay.interfaces.YCPaymentCallBackImpl;
import com.youcanPay.models.YCPayBalanceResult;
import com.youcanPay.models.YCPayResult;
import com.youcanPay.models.YCPaymentCallBakParams;
import com.youcanPay.services.ApiServices;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.youcanPay.config.YCPayConfig.UNEXPECTED_ERROR;
import static com.youcanPay.config.YCPayConfig.YCP_TAG;

public class ViewModelPaymentCallBack {

    String urlPaymentCallBack;

    public ViewModelPaymentCallBack(String urlPaymentCallBack) {
        this.urlPaymentCallBack = urlPaymentCallBack;
    }

    ApiProviderPaymentCallBack apiProvider;

    public void callPaymentCallBAck(YCPaymentCallBakParams params, HashMap<String, String> body, YCPaymentCallBackImpl paymentCallBack) {

        apiProvider = ApiServices.getClientPaymentCallBack(urlPaymentCallBack).create(ApiProviderPaymentCallBack.class);
        Call<YCPayBalanceResult> call = apiProvider.callPaymentCall(params.getHeader(), body);

        call.enqueue(callBack(paymentCallBack));
    }

    Callback<YCPayBalanceResult> callBack(YCPaymentCallBackImpl paymentCallBack) {
        return new Callback<YCPayBalanceResult>() {
            @Override
            public void onResponse(Call<YCPayBalanceResult> call, Response<YCPayBalanceResult> response) {
                YCPayBalanceResult result;

                if (response.isSuccessful()) {
                    result = response.body();
                    paymentCallBack.onSuccess(result.getStatus());

                    return;
                }

                onCallResponseError(response, paymentCallBack);
            }

            @Override
            public void onFailure(Call<YCPayBalanceResult> call, Throwable t) {
                paymentCallBack.onError(UNEXPECTED_ERROR);
            }
        };
    }

    void onCallResponseError(Response<YCPayBalanceResult> response, YCPaymentCallBackImpl paymentCallBack) {

        String errorBody = "";

        try {
            errorBody = response.errorBody().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.e(YCP_TAG, "onPayFailure: " + errorBody);

        YCPayResult result = new YCPayResult().resultFromJson(errorBody);

        paymentCallBack.onError(result.message);
    }
}
