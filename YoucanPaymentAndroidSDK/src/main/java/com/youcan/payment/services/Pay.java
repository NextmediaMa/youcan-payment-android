package com.youcan.payment.services;

import android.util.Log;

import com.youcan.payment.YCPay;
import com.youcan.payment.config.YCPayConfig;
import com.youcan.payment.instrafaces.PayCallBackImpl;
import com.youcan.payment.models.YCPayCardInformation;
import com.youcan.payment.models.YCPayToken;
import com.youcan.payment.task.PayTask;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class Pay {

    String payUrl = YCPayConfig.URL_PAY;
    YCPayCardInformation cardInformation;
    YCPayToken token;
    String TAG = "YCPay";

    public Pay() {
    }

    /**
     * Set you user card Information
     *
     * @param cardInformation card data
     * @return Pay instance
     */
    public Pay setCardInformation(YCPayCardInformation cardInformation) {
        this.cardInformation = cardInformation;

        return this;
    }

    public Pay setPayUrl(String payUrl) {
        this.payUrl = payUrl;

        return this;
    }

    public Pay setToken(YCPayToken token) {
        this.token = token;

        return this;
    }

    public YCPayToken getToken() {
        return token;
    }

    /**
     * Set a listener to your payments result
     *
     * @param payListener PayCallBackImpl
     * @return Pay instance
     */
    public Pay setListener(PayCallBackImpl payListener) {
        YCPay.payListener = payListener;

        return this;
    }

    /**
     * Call method allow you to effect your payment
     */
    public void call() {
        if (token == null) {
            Log.e(TAG, "Token: Token is null");

            return;
        }

        if (YCPay.payListener == null) {
            Log.e(TAG, "payListener: listener null");

            return;
        }

        RequestBody form = new FormBody.Builder()
                .add("card_holder_name", this.cardInformation.getCardHolderName())
                .add("cvv", this.cardInformation.getCvv())
                .add("credit_card", this.cardInformation.getCardNumber())
                .add("expire_date", this.cardInformation.getExpireDate())
                .add("token_id", token.getId())
                .add("pub_key", "")
                .add("is_mobile", "1")
                .build();

        new PayTask(this.payUrl, form, YCPay.payListener).execute("");
    }
}
