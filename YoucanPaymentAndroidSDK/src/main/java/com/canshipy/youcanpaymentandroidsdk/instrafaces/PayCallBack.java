package com.canshipy.youcanpaymentandroidsdk.instrafaces;

import com.canshipy.youcanpaymentandroidsdk.models.YCPayResult;

public interface PayCallBack {
    void onPaySuccess(YCPayResult result);
    void onPayFailure(String message);
    void on3DsResult(YCPayResult result);
}
