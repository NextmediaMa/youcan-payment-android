package com.youcanPay.services;

import com.youcanPay.exception.YCPayInvalidArgumentException;
import com.youcanPay.exception.YCPayInvalidResponseException;
import com.youcanPay.factories.YCPAccountConfigFactory;
import com.youcanPay.interfaces.HttpCallBackImpl;
import com.youcanPay.models.HttpResponse;
import com.youcanPay.models.YCPayAccountConfig;
import com.youcanPay.networking.YCPayHTTPAdapter;

import org.json.JSONException;

import java.util.HashMap;

import static com.youcanPay.config.YCPayConfig.API_URL;
import static com.youcanPay.config.YCPayConfig.API_URL_SANDBOX;
import static com.youcanPay.utils.YCPayLocale.getLocale;

public class YCPayGetConfigService implements HttpCallBackImpl {
    private final YCPayHTTPAdapter httpAdapter;
    public YCPayAccountConfig ycPayAccountConfig = new YCPayAccountConfig();

    public YCPayGetConfigService(YCPayHTTPAdapter httpAdapter) {
        this.httpAdapter = httpAdapter;
        this.httpAdapter.setHttpCallback(this);
    }

    public void getAccountConfig(String pubKey) throws YCPayInvalidArgumentException {
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Accept", "application/json");
        header.put("X-Preferred-Locale", getLocale());

        httpAdapter.get(
                getEndpoint("api/get-account-configs/" + pubKey),
                new HashMap(),
                header);
    }

    private String getEndpoint(String path) {
        return httpAdapter.getSandboxMode() ? API_URL_SANDBOX + path : API_URL + path;
    }

    @Override
    public void onResponse(HttpResponse response) {
        try {
            this.ycPayAccountConfig = YCPAccountConfigFactory.getResponse(response.getBody());
        } catch (JSONException | YCPayInvalidResponseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String message) {
    }
}
