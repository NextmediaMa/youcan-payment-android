package com.youcanPay.services;

import android.content.Context;

import com.youcanPay.exception.YCPayInvalidArgumentException;
import com.youcanPay.exception.YCPayInvalidDecodedJSONException;
import com.youcanPay.exception.YCPayInvalidResponseException;
import com.youcanPay.factories.YCPResponseFactory;
import com.youcanPay.interfaces.HttpCallBackImpl;
import com.youcanPay.interfaces.PayCallbackImpl;
import com.youcanPay.models.HttpResponse;
import com.youcanPay.models.YCPResponse3ds;
import com.youcanPay.models.YCPResponseCashPlus;
import com.youcanPay.models.YCPResponseSale;
import com.youcanPay.models.YCPayAccountConfig;
import com.youcanPay.models.YCPayCardInformation;
import com.youcanPay.models.YCPayResponse;
import com.youcanPay.networking.YCPayHTTPAdapter;
import com.youcanPay.view.YCPayBottomSheet;

import java.util.HashMap;

import static com.youcanPay.config.YCPayConfig.API_URL;
import static com.youcanPay.config.YCPayConfig.API_URL_SANDBOX;
import static com.youcanPay.utils.YCPayLocale.getLocale;

public class YCPayApiService implements HttpCallBackImpl {
    private final YCPayHTTPAdapter httpAdapter;
    private Context context;
    private PayCallbackImpl payCallback;

    public YCPayApiService(YCPayHTTPAdapter httpAdapter) {
        this.httpAdapter = httpAdapter;
        this.httpAdapter.setHttpCallback(this);
    }

    public void setSandBoxMode(boolean isSandboxMode) {
        this.httpAdapter.setSandboxMode(isSandboxMode);
    }

    public void payWithCard(
            Context context,
            String pubKey, String tokenId,
            YCPayCardInformation cardInformation,
            PayCallbackImpl payCallback

    ) throws YCPayInvalidArgumentException, NullPointerException {
        if (payCallback == null) {
            throw new NullPointerException("Null Exception: Pay Callback is null");
        }

        if (cardInformation == null) {
            throw new NullPointerException("Null Exception: cardInformation is null");
        }

        this.context = context;
        this.payCallback = payCallback;

        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Accept", "application/json");
        header.put("X-Preferred-Locale", getLocale());

        HashMap<String, String> params = cardInformation.toHashMap();
        params.put("token_id", tokenId);
        params.put("pub_key", pubKey);
        params.put("is_mobile", "1");

        httpAdapter.post(
                getEndpoint("api/pay"),
                params,
                header);
    }

    public void payWithCashPlus(
            Context context,
            String pubKey, String tokenId,
            PayCallbackImpl payCallback

    ) throws YCPayInvalidArgumentException, NullPointerException {
        if (payCallback == null) {
            throw new NullPointerException("Null Exception: Pay Callback is null");
        }

        this.context = context;
        this.payCallback = payCallback;

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

    private void show3DsSheet(
            Context context,
            YCPResponse3ds ycPayResult
    ) {
        YCPayBottomSheet bottomDialog = new YCPayBottomSheet(context, ycPayResult, this.payCallback);
        bottomDialog.show();
    }

    private String getEndpoint(String path) {
        return httpAdapter.getSandboxMode() ? API_URL_SANDBOX + path : API_URL + path;
    }

    @Override
    public void onResponse(HttpResponse response) {
        YCPayResponse result;
        try {
            result = YCPResponseFactory.getResponse(response);

            if (result instanceof YCPResponse3ds) {
                show3DsSheet(this.context, ((YCPResponse3ds) result));

                return;
            }

            if (result instanceof YCPResponseCashPlus) {
                this.payCallback.onSuccess(((YCPResponseCashPlus) result).getToken());

                return;
            }

            YCPResponseSale responseSale = (YCPResponseSale) result;

            if (!responseSale.isSuccess()) {
                this.payCallback.onFailure(responseSale.getMessage());

                return;
            }

            this.payCallback.onSuccess(result.getTransactionId());

        } catch (YCPayInvalidResponseException | YCPayInvalidDecodedJSONException e) {
            e.printStackTrace();
            this.onError(e.getMessage());
        }
    }

    @Override
    public void onError(String message) {
        this.payCallback.onFailure(message);
    }
}
