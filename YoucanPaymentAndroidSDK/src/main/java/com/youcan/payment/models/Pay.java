package com.youcan.payment.models;

import android.util.Log;

import com.youcan.payment.YCPay;
import com.youcan.payment.config.Config;
import com.youcan.payment.instrafaces.PayCallBackImpl;
import com.youcan.payment.task.PayTask;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class Pay {

    String payUrl = Config.URL_PAY;
    CardInformation cardInformation;
    YCPayToken token;

    public Pay() {
    }

    public Pay setCardInformation(CardInformation cardInformation) {
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

    public Pay setListener(PayCallBackImpl payListener) {
        YCPay.payListener = payListener;

        return this;
    }

    public void call() {
        if (token == null) {
            Log.e("result", "Token: Token is null");

            return;
        }

        if (YCPay.payListener == null) {
            Log.e("result", "call: listener null");

            return;
        }

        RequestBody form = new FormBody.Builder()
                .add("card_holder_name", this.cardInformation.getCardHolderName())
                .add("cvv", this.cardInformation.getCvv())
                .add("credit_card", this.cardInformation.getCardNumber())
                .add("expire_date", this.cardInformation.getExpireDate())
                .add("token_id", token.id)
                .add("pub_key", Config.PUB_KEY)
                .add("is_mobile", "1")
                .build();

        new PayTask(this.payUrl, form, YCPay.payListener).execute("");
    }
}
