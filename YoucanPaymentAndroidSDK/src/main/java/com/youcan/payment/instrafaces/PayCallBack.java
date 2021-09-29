package com.youcan.payment.instrafaces;

import com.youcan.payment.models.YCPayResult;

public interface PayCallBack {
    void onPaySuccess(YCPayResult result);
    void onPayFailure(String message);
    void on3DsResult(YCPayResult result);
}
