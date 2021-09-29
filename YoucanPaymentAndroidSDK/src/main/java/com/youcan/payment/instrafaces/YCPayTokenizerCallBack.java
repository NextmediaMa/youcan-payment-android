package com.youcan.payment.instrafaces;

import com.youcan.payment.models.YCPayToken;

public interface YCPayTokenizerCallBack {
    void onResponse(YCPayToken token);

    void onError(String response);
}
