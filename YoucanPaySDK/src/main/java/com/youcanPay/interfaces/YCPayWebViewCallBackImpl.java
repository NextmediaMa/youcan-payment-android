package com.youcanPay.interfaces;

public interface YCPayWebViewCallBackImpl {
    void onPaySuccess();

    void onPayFailure(String message);
}
