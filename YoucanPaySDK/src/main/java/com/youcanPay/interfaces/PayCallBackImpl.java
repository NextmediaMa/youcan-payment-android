package com.youcanPay.interfaces;

import com.youcanPay.models.YCPayResult;

/**
 * PayCallBackImpl allow you to catch pay response
 */

public interface PayCallBackImpl {
    void onPaySuccess(YCPayResult result);

    void onPayFailure(String message);

    void on3DsResult(YCPayResult result);
}
