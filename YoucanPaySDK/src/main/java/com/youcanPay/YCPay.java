package com.youcanPay;

import android.content.Context;
import android.util.Log;

import com.youcanPay.interfaces.PayCallbackImpl;
import com.youcanPay.models.YCPayCardInformation;
import com.youcanPay.services.ApiService;

import static com.youcanPay.config.YCPayConfig.YCP_TAG;

public class YCPay {
    private final String pubKey;
    private String locale = "en";
    private boolean isSandboxMode = false;
    private final Context context;

    public YCPay(Context context, String pubKey) {
        this.pubKey = pubKey;
        this.context = context;
    }

    public YCPay(Context context, String pubKey, String locale) {
        this.pubKey = pubKey;
        this.locale = locale;
        this.context = context;
    }

    /**
     * config SandBox mode
     *
     * @param isSandboxMode SandBox status
     */
    public void setSandboxMode(boolean isSandboxMode) {
        this.isSandboxMode = isSandboxMode;
    }

    /**
     * Call method allow you to effect your payment
     */
    public void pay(String tokenId, YCPayCardInformation cardInformation, PayCallbackImpl payCallBack) throws Exception {

        if (payCallBack == null) {
            throw new Exception("Null Exception: Listener is null");
        }

        if (cardInformation == null) {
            throw new Exception("Null Exception: cardInformation is null");
        }

        if (!cardInformation.isCardValid()) {
            throw new Exception("cardInformation Exception: Invalid card data");
        }

        Log.e(YCP_TAG, "isSandboxMode: YC PAY " + this.isSandboxMode);

        ApiService apiService = new ApiService(context, this.isSandboxMode, this.locale);
        apiService.pay(cardInformation, payCallBack, this.pubKey, tokenId);
    }

}
