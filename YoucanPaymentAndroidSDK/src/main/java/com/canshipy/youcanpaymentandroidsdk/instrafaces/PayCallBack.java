package com.canshipy.youcanpaymentandroidsdk.instrafaces;

import com.canshipy.youcanpaymentandroidsdk.models.Result;

public interface PayCallBack {
    void onPaySuccess(Result result);
    void onPayFailure(String message);
    void on3DsResult(Result result);
}
