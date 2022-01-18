package com.youcanPay.interfaces;

/**
 * PayCallBackImpl allow you to catch pay response
 */
    public interface CashPlusCallbackImpl {
    void onSuccess(String transactionId, String token);

    void onFailure(String message);
}
