package com.youcanPay.networking;

import java.util.HashMap;

abstract public class HTTPAdapter {
    protected boolean isSandboxMode;

    public HTTPAdapter(boolean isSandboxMode) {
        this.isSandboxMode = isSandboxMode;
    }

    public void post(String endpoint, HashMap<String, String> params,
                     HashMap<String, String> headers) throws Exception {
        this.request("POST", endpoint, params, headers);
    }

    public void get(String endpoint, HashMap<String, String> params,
                    HashMap<String, String> headers) throws Exception {
        this.request("GET", endpoint, params, headers);
    }

    abstract public void request(String method, String endpoint, HashMap<String, String> params,
                                 HashMap<String, String> headers) throws Exception;
}
