package com.youcanPay;

import android.content.Context;
import android.util.Log;

import com.youcanPay.interfaces.PayCallbackImpl;
import com.youcanPay.models.YCPayCardInformation;
import com.youcanPay.models.YCPayResult;
import com.youcanPay.services.ApiService;

import static com.youcanPay.config.YCPayConfig.YCP_TAG;

public class YCPay {
    private final String pubKey;
    private String tokenId = "";
    private String locale = "en";
    private boolean isSandboxMode = false;
    private Context context;
    private ApiService apiService;

    public YCPay(Context context, String pubKey) {
        this.pubKey = pubKey;
        this.context = context;
    }

    public YCPay(Context context, String pubKey, String locale) {
        this.pubKey = pubKey;
        this.locale = locale;
        this.context = context;
    }

    public String getTokenId() {
        return this.tokenId;
    }

    /**
     * config SandBox mode
     *
     * @param isSandboxMode SandBox status
     * @return YCPay instance
     */
    public YCPay setSandboxMode(boolean isSandboxMode) {
        this.isSandboxMode = isSandboxMode;

        return this;
    }

    /**
     * Call method allow you to effect your payment
     */
    public void pay(String tokenId, YCPayCardInformation cardInformation, PayCallbackImpl payCallBack) throws Exception {

        this.tokenId = tokenId;

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

        apiService = new ApiService(context, this.isSandboxMode);
        apiService.pay(cardInformation, payCallBack, this.pubKey, tokenId, this.locale);
    }

    /**
     * load3DSPage is allow to load 3Ds pages
     * and send it to Your YCPayWebView
     *
     * @param result result of on3DsResult in payListener
     */
    public void load3DsPage(YCPayResult result) {

        //TODO: get 3Ds Html Page in sendBox Mode
    }
}
