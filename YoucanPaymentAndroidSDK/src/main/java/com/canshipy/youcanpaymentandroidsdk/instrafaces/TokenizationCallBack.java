package com.canshipy.youcanpaymentandroidsdk.instrafaces;

import com.canshipy.youcanpaymentandroidsdk.models.Token;


public interface TokenizationCallBack {
    void onResponse(Token token);
    void onError(String response);
}
