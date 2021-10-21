package com.youcanPay.viewModel;

import com.youcanPay.api.ApiProvider;
import com.youcanPay.interfaces.PayCallBackImpl;
import com.youcanPay.models.YCPayCardInformation;
import com.youcanPay.models.YCPayResult;
import com.youcanPay.models.YCPayToken;
import com.youcanPay.services.Services;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewModelPay {
    ApiProvider apiProvider = Services.getClient().create(ApiProvider.class);

    public void callPay(YCPayCardInformation cardInformation, PayCallBackImpl payCallBack, YCPayToken token) {
        apiProvider = Services.getClient().create(ApiProvider.class);

        HashMap<String, String> body = cardInformation.toHashMap();
        body.put("token_id", token.getTransactionId());
        body.put("pub_key", token.getPubKey());
        body.put("is_mobile", "1");

        Call<YCPayResult> call = apiProvider.pay(body);

        call.enqueue(new Callback<YCPayResult>() {
            @Override
            public void onResponse(Call<YCPayResult> call, Response<YCPayResult> response) {
                YCPayResult result;
                if (response.isSuccessful()) {
                    result = response.body();

                    onPaySuccessful(result, payCallBack);

                    return;
                }

                onPayFailure(response, payCallBack);
            }

            @Override
            public void onFailure(Call<YCPayResult> call, Throwable t) {
                payCallBack.onPayFailure(t.getLocalizedMessage());
            }
        });
    }

    void onPaySuccessful(YCPayResult result, PayCallBackImpl payCallBack) {
        if (!result.redirectUrl.equals("") && !result.returnUrl.equals("")) {
            payCallBack.on3DsResult(result);

            return;
        }

        if (result.success) {
            payCallBack.onPaySuccess(result);
        }
    }

    void onPayFailure(Response<YCPayResult> response, PayCallBackImpl payCallBack) {
        String errorBody = "";

        try {
            errorBody = response.errorBody().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        YCPayResult result = new YCPayResult().resultFromJson(errorBody);

        payCallBack.onPayFailure(result.message);
    }
}
