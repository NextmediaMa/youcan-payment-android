package com.youcan.payment;

import android.util.Log;

import com.youcan.payment.config.YCPayConfig;
import com.youcan.payment.interfaces.PayCallBackImpl;
import com.youcan.payment.models.YCPayCardInformation;
import com.youcan.payment.services.YCPaymentCallBack;
import com.youcan.payment.models.YCPayResult;
import com.youcan.payment.models.YCPayToken;
import com.youcan.payment.task.PayTask;
import com.youcan.payment.task.TestExecutors;
import com.youcan.payment.task.YCPayLoad3DSPageTask;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class YCPay {

    public YCPayToken token;
    boolean isSandboxMode = false;
    /**
     * PayCallBackImpl allow you to catch pay response
     * onPaySuccess / onPayFailure / on3DsResult
     */
    public PayCallBackImpl payListener;

    /**
     * config SandBox mode
     *
     * @param isActive SandBox status
     * @return YCPay instance
     */
    public YCPay setSandboxMode(boolean isActive) {
        isSandboxMode = isActive;

        return this;
    }

    /**
     * allow you to call Payment callback
     * to notify your back end state of payment
     */
    public YCPaymentCallBack ycPaymentCallBack = new YCPaymentCallBack();
    String TAG = "YCPay";
    String payUrl = YCPayConfig.URL_PAY;

    public YCPay(String pubKey, String tokenId) {
        this.token = new YCPayToken(tokenId, pubKey);
    }

    /**
     * Call method allow you to effect your payment
     */
    public void pay(YCPayCardInformation cardInformation, PayCallBackImpl payCallBack) throws Exception {
        this.payListener = payCallBack;

        if (token == null) {
            Log.e(TAG, "Token: Token is null");

            throw new Exception("Null Exception: Token is null");
        }

        if (payListener == null) {

            throw new Exception("Null Exception: Listener is null");
        }

        if (cardInformation == null) {

            throw new Exception("Null Exception: cardInformation is null");
        }

        RequestBody form = new FormBody.Builder()
                .add("card_holder_name", cardInformation.getCardHolderName())
                .add("cvv", cardInformation.getCvv())
                .add("credit_card", cardInformation.getCardNumber())
                .add("expire_date", cardInformation.getExpireDate())
                .add("token_id", token.getTransactionId())
                .add("pub_key", token.getPubKey())
                .add("is_mobile", "1")
                .build();

        new PayTask(this.payUrl, form, this.payListener).execute("");
        //new TestExecutors(this.payUrl, form, this.payListener).call();
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

        new YCPayLoad3DSPageTask(result.redirectUrl, result.listenUrl, form, payListener).execute("");
    }
}
