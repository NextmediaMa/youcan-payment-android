package com.youcan.payment.instrafaces;

public interface YCPaymentCallBackImpl {
    void onSuccess(int statusResult);

    void onError(String message);
}
