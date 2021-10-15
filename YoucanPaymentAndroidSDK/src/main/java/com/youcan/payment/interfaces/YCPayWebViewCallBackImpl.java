package com.youcan.payment.interfaces;

public interface YCPayWebViewCallBackImpl {
    void onPaySuccess();

    void onPayFailure(String message);
}
