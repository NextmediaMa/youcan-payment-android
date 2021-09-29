package com.canshipy.youcanpaymentandroidsdk.instrafaces;

import com.canshipy.youcanpaymentandroidsdk.models.YCPayToken;

public interface YCPayTokenizerCallBack {
    void onResponse(YCPayToken token);

    void onError(String response);
}
