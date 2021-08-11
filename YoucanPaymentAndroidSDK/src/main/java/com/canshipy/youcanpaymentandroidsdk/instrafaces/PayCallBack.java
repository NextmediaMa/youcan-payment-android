package com.canshipy.youcanpaymentandroidsdk.instrafaces;

import com.canshipy.youcanpaymentandroidsdk.models.Result;

public interface PayCallBack {
    void onPaySuccess(Result result);
    void onPayField(String response);
}
