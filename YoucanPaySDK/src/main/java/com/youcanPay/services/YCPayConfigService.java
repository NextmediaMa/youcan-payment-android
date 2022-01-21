package com.youcanPay.services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

public class YCPayConfigService implements HttpCallBackImpl {
    private final YCPayHTTPAdapter httpAdapter;
    public MutableLiveData<YCPayAccountConfig> ycPayAccountConfigLiveData;
    public YCPayAccountConfig accountConfig = new YCPayAccountConfig();

    public YCPayConfigService(YCPayHTTPAdapter httpAdapter) {
        this.httpAdapter = httpAdapter;
        this.httpAdapter.setHttpCallback(this);
        this.ycPayAccountConfigLiveData = new MutableLiveData<>();
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

    public LiveData<YCPayAccountConfig> getAccountConfig() {
        return this.ycPayAccountConfigLiveData;
    }

    @Override
    public void onResponse(HttpResponse response) {
        try {
            accountConfig = YCPAccountConfigFactory.getResponse(response.getBody());
            this.ycPayAccountConfigLiveData.postValue(accountConfig);
        } catch (JSONException | YCPayInvalidResponseException e) {
            accountConfig.setMessage(e.getMessage());
            this.onError(e.getMessage());
        }
    }

    @Override
    public void onError(String message) {
        YCPayAccountConfig accountConfig = new YCPayAccountConfig();
        accountConfig.setMessage(message);
        this.ycPayAccountConfigLiveData.postValue(accountConfig);
    }

}
