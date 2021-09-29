package com.youcan.payment.instrafaces;

import com.youcan.payment.models.YCPayToken;

public interface YCPayTokenizerCallBackImpl {
    void onResponse(YCPayToken token);

    void onError(String response);
}
