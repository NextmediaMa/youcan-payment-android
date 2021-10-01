package com.youcan.payment;

import com.youcan.payment.instrafaces.PayCallBackImpl;
import com.youcan.payment.services.YCPaymentCallBack;
import com.youcan.payment.services.YCPayTokenizer;
import com.youcan.payment.services.Pay;
import com.youcan.payment.models.YCPayResult;
import com.youcan.payment.models.YCPayToken;
import com.youcan.payment.task.YCPayLoad3DSPageTask;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class YCPay {

    static public Pay pay = new Pay();

    /**
     * PayCallBackImpl allow you to catch pay response
     * onPaySuccess / onPayFailure / on3DsResult
     */
    static public PayCallBackImpl payListener;

    /**
     * allow you to create your Token
     */
    static public YCPayTokenizer ycPayTokenizer = new YCPayTokenizer();

    /**
     * allow you to call Payment callback
     * to notify your back end state of payment
     */
    static public YCPaymentCallBack ycPaymentCallBack = new YCPaymentCallBack();

    /**
     * set Token
     *
     * @param tokenId
     */
    static public void setTokenId(String tokenId) {
        YCPayToken token = new YCPayToken();
        token.setId(tokenId);
        pay.setToken(token);
    }

    /**
     * load3DSPage is allow to load 3Ds pages
     * and send it to Your YCPayWebView
     *
     * @param result result of on3DsResult in payListener
     */
    static public void load3DsPage(YCPayResult result) {
        RequestBody form = new FormBody.Builder()
                .add("PaReq", result.paReq)
                .add("MD", result.transactionId)
                .add("TermUrl", result.callBackUrl)
                .build();

        new YCPayLoad3DSPageTask(result.redirectUrl, result.listenUrl, form).execute("");
    }
}
