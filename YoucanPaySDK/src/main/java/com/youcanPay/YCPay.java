package com.youcanPay;

import android.content.Context;

import com.youcanPay.config.YCPayConfig;
import com.youcanPay.interfaces.PayCallbackImpl;
import com.youcanPay.models.YCPayCardInformation;
import com.youcanPay.services.ApiService;

public class YCPay {
    private final String pubKey;
    private boolean isSandboxMode = false;
    private final Context context;

    public YCPay(Context context, String pubKey) {
        this.pubKey = pubKey;
        this.context = context;
    }

    public YCPay(Context context, String pubKey, String locale) {
        this.pubKey = pubKey;
        this.context = context;
        YCPayConfig.locale = locale;
    }

    /**
     * config SandBox mode
     *
     * @param isSandboxMode SandBox status
     */
    public void setSandboxMode(boolean isSandboxMode) {
        this.isSandboxMode = isSandboxMode;
    }

    public void pay(String tokenId, YCPayCardInformation cardInformation, PayCallbackImpl payCallBack) throws Exception {
        ApiService apiService = new ApiService(context, this.isSandboxMode, cardInformation, payCallBack, this.pubKey, tokenId);
        apiService.pay();
    }
}
