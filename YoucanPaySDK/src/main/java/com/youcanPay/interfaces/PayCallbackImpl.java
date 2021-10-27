package com.youcanPay.interfaces;

import com.youcanPay.models.YCPayResult;

/**
 * PayCallBackImpl allow you to catch pay response
 */

public interface PayCallbackImpl {
    void onSuccess(String transactionId);

    void onFailure(String message);

    //void on3DsResult(YCPayResult result);
}
