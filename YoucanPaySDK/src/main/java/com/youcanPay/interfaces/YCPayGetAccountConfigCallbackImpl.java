package com.youcanPay.interfaces;

import com.youcanPay.models.YCPayAccountConfig;

/**
 * PayCallBackImpl allow you to catch get account config response
 */
public interface YCPayGetAccountConfigCallbackImpl {
    void onGetAccountConfig(YCPayAccountConfig accountConfig);

    void onAccountConfigFailure(String message);
}
