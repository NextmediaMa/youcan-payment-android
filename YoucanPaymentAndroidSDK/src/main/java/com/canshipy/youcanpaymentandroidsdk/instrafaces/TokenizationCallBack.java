package com.canshipy.youcanpaymentandroidsdk.instrafaces;

import com.canshipy.youcanpaymentandroidsdk.models.Token;

import okhttp3.Response;

public interface TokenizationCallBack {
    void onResponse(Token token);
    void onError(String response);
}
