package com.youcan.payment.instrafaces;

import com.youcan.payment.models.YCPayBalanceResult;
public interface YCPayBalanceCallBackImpl {
    void onResponse(YCPayBalanceResult balanceResult);

    void onError(String response);
}
