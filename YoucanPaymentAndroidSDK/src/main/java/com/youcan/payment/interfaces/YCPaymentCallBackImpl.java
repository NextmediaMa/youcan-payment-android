package com.youcan.payment.interfaces;

public interface YCPaymentCallBackImpl {
    void onSuccess(int statusResult);

    void onError(String message);
}
