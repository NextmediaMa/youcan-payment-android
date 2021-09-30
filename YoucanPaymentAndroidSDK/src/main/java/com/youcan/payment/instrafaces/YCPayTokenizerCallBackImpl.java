package com.youcan.payment.instrafaces;

import com.youcan.payment.models.YCPayToken;

public interface YCPayTokenizerCallBackImpl {
    void onSuccess(YCPayToken token);

    void onError(String message);
}
