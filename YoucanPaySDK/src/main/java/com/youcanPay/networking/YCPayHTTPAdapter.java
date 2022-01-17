package com.youcanPay.networking;

import com.youcanPay.exception.YCPayInvalidArgumentException;
import com.youcanPay.interfaces.HttpCallBackImpl;
import com.youcanPay.interfaces.YCPayHttpCallbackImpl;

import java.util.HashMap;

abstract public class YCPayHTTPAdapter {
    protected boolean isSandboxMode;
    protected HttpCallBackImpl httpCallback;

    public YCPayHTTPAdapter() {
    }

    public void setSandboxMode(boolean isSandboxMode) {
        this.isSandboxMode = isSandboxMode;
    }

    public boolean getSandboxMode() {
        return this.isSandboxMode;
    }

    public void post(
            String endpoint,
            HashMap<String, String> params,
            HashMap<String, String> headers
    ) throws YCPayInvalidArgumentException {
        this.request("POST", endpoint, params, headers);
    }

    public void setHttpCallback(HttpCallBackImpl httpCallback) {
        this.httpCallback = httpCallback;
    }

    public void get(
            String endpoint,
            HashMap<String, String> params,
            HashMap<String, String> headers
    ) throws YCPayInvalidArgumentException {
        this.request("GET", endpoint, params, headers);
    }

    abstract public void request(
            String method,
            String endpoint,
            HashMap<String, String> params,
            HashMap<String, String> headers
    ) throws YCPayInvalidArgumentException;
}
