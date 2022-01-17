package com.youcanPay.interfaces;

/**
 * PayCallBackImpl allow you to catch pay response
 */
public interface PayCallbackImpl {
    void onSuccess(String transactionId);

    void onFailure(String message);
}
