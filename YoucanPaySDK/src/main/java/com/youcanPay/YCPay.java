package com.youcanPay;

import com.youcanPay.interfaces.PayCallBackImpl;
import com.youcanPay.models.YCPayCardInformation;
import com.youcanPay.models.YCPayResult;
import com.youcanPay.models.YCPayToken;
import com.youcanPay.task.YCPayLoad3DSPageTask;
import com.youcanPay.viewModel.ViewModelPay;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class YCPay {

    public YCPayToken token;
    private boolean isSandboxMode = false;
    public PayCallBackImpl payCallBack;
    public YCPaymentCallBack ycPaymentCallBack = new YCPaymentCallBack();
    private final ViewModelPay viewModelPay = new ViewModelPay();

    public YCPay(String pubKey, String tokenId) {
        this.token = new YCPayToken(tokenId, pubKey);
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
    public void pay(YCPayCardInformation cardInformation, PayCallBackImpl payCallBack) throws Exception {
        this.payCallBack = payCallBack;

        if (token == null) {
            throw new Exception("Null Exception: Token is null");
        }

        if (this.payCallBack == null) {
            throw new Exception("Null Exception: Listener is null");
        }

        if (cardInformation == null) {
            throw new Exception("Null Exception: cardInformation is null");
        }

        viewModelPay.callPay(cardInformation, payCallBack, token);
    }

    /**
     * load3DSPage is allow to load 3Ds pages
     * and send it to Your YCPayWebView
     *
     * @param result result of on3DsResult in payListener
     */
    public void load3DsPage(YCPayResult result) {
        RequestBody form = new FormBody.Builder()
                .add("PaReq", result.paReq)
                .add("MD", result.transactionId)
                .add("TermUrl", result.returnUrl)
                .build();

        new YCPayLoad3DSPageTask(result.redirectUrl, result.listenUrl, form, payCallBack).execute("");
    }
}
