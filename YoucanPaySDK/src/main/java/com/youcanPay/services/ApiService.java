package com.youcanPay.services;

import android.content.Context;

import com.youcanPay.interfaces.HttpCallbackImpl;
import com.youcanPay.interfaces.PayCallbackImpl;
import com.youcanPay.models.YCPResponse3ds;
import com.youcanPay.models.YCPResponseSale;
import com.youcanPay.models.YCPayCardInformation;
import com.youcanPay.models.YCPayResponse;
import com.youcanPay.networking.HTTPAdapter;
import com.youcanPay.networking.RetrofitHTTPAdapter;
import com.youcanPay.view.YCPayBottomSheet;

import java.util.HashMap;

import static com.youcanPay.config.YCPayConfig.API_URL;
import static com.youcanPay.config.YCPayConfig.API_URL_SANDBOX;
import static com.youcanPay.config.YCPayConfig.locale;

public class ApiService implements HttpCallbackImpl {
    private final HTTPAdapter httpAdapter;
    private Context context;
    private PayCallbackImpl payCallback;
    private final boolean isSandboxMode;

    public ApiService(boolean isSandboxMode) {
        this.isSandboxMode = isSandboxMode;
        this.httpAdapter = new RetrofitHTTPAdapter(isSandboxMode, this);
    }

    public void pay(Context context, String pubKey, String tokenId,
                    YCPayCardInformation cardInformation, PayCallbackImpl payCallback) throws Exception {
        if (payCallback == null) {
            throw new NullPointerException("Null Exception: Pay Callback is null");
        }

        if (cardInformation == null) {
            throw new NullPointerException("Null Exception: cardInformation is null");
        }

        this.context = context;
        this.payCallback = payCallback;

        HashMap<String, String> params = cardInformation.toHashMap();
        params.put("token_id", tokenId);
        params.put("pub_key", pubKey);
        params.put("is_mobile", "1");

        HashMap<String, String> header = cardInformation.toHashMap();
        header.put("Accept", "application/json");
        header.put("X-Preferred-Locale", locale);

        httpAdapter.post(this.isSandboxMode ? API_URL_SANDBOX + "api/pay" : API_URL + "api/pay",
                params,
                header);
    }

    private void show3DsSheet(Context context, YCPResponse3ds ycPayResult) {
        YCPayBottomSheet bottomDialog = new YCPayBottomSheet(context, ycPayResult, this.payCallback);
        bottomDialog.show();
    }

    @Override
    public void onResponse(YCPayResponse response) {
        if (response instanceof YCPResponse3ds) {
            show3DsSheet(this.context, ((YCPResponse3ds) response));

            return;
        }

        YCPResponseSale responseSale = (YCPResponseSale) response;

        if (!responseSale.isSuccess()) {
            this.payCallback.onFailure(responseSale.getMessage());

            return;
        }

        this.payCallback.onSuccess(response.getTransactionId());
    }

    @Override
    public void onError(String message) {
        this.payCallback.onFailure(message);
    }
}
