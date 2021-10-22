package com.youcanPay.interfaces;

public interface YCPaymentCallBackImpl {
    void onSuccess(int statusResult);

    void onError(String message);
}
