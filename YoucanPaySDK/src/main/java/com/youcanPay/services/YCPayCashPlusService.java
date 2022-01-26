package com.youcanPay.services;


import com.youcanPay.exception.YCPayInvalidArgumentException;
import com.youcanPay.exception.YCPayInvalidDecodedJSONException;
import com.youcanPay.exception.YCPayInvalidResponseException;
import com.youcanPay.factories.YCPResponseFactory;
import com.youcanPay.interfaces.CashPlusCallbackImpl;
import com.youcanPay.interfaces.HttpCallBackImpl;
import com.youcanPay.models.HttpResponse;
import com.youcanPay.models.YCPResponseCashPlus;
import com.youcanPay.models.YCPResponseSale;
import com.youcanPay.models.YCPayResponse;
import com.youcanPay.networking.YCPayHTTPAdapter;

import java.util.HashMap;

import static com.youcanPay.config.YCPayConfig.API_URL;
import static com.youcanPay.config.YCPayConfig.API_URL_SANDBOX;
import static com.youcanPay.utils.YCPayLocale.getLocale;

public class YCPayCashPlusService implements HttpCallBackImpl {
    private final YCPayHTTPAdapter httpAdapter;
    private CashPlusCallbackImpl cashPlusCallback;

    public YCPayCashPlusService(YCPayHTTPAdapter httpAdapter) {
        this.httpAdapter = httpAdapter;
        this.httpAdapter.setHttpCallback(this);
    }

    public void setSandBoxMode(boolean isSandboxMode) {
        this.httpAdapter.setSandboxMode(isSandboxMode);
    }

    public void payWithCashPlus(
            String pubKey,
            String tokenId,
            CashPlusCallbackImpl cashPlusCallback

    ) throws YCPayInvalidArgumentException, NullPointerException {
        if (cashPlusCallback == null) {
            throw new NullPointerException("Null Exception: Pay Callback is null");
        }

        this.cashPlusCallback = cashPlusCallback;

        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Accept", "application/json");
        header.put("X-Preferred-Locale", getLocale());

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token_id", tokenId);
        params.put("pub_key", pubKey);

        httpAdapter.post(
                getEndpoint("api/cashplus/init"),
                params,
                header);
    }

    private String getEndpoint(String path) {
        return httpAdapter.getSandboxMode() ? API_URL_SANDBOX + path : API_URL + path;
    }

    @Override
    public void onResponse(HttpResponse response) {
        YCPayResponse result;

        try {
            result = YCPResponseFactory.getResponse(response);
            if (!response.isSuccess()) {
                this.cashPlusCallback.onFailure(((YCPResponseSale) result).getMessage());

                return;
            }

            this.cashPlusCallback.onSuccess(
                    result.getTransactionId(),
                    ((YCPResponseCashPlus) result).getToken()
            );

        } catch (YCPayInvalidResponseException | YCPayInvalidDecodedJSONException | ClassCastException e) {
            e.printStackTrace();
            this.onError(e.getMessage());
        }
    }

    @Override
    public void onError(String message) {
        this.cashPlusCallback.onFailure(message);
    }
}
