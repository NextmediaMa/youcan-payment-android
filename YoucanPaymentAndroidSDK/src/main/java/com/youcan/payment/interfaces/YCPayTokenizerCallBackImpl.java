package com.youcan.payment.interfaces;

import com.youcan.payment.models.YCPayToken;

public interface YCPayTokenizerCallBackImpl {
    void onSuccess(YCPayToken token);

    void onError(String message);
}
