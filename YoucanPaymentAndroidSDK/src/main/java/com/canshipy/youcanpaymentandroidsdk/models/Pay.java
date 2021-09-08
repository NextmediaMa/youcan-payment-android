package com.canshipy.youcanpaymentandroidsdk.models;

import android.util.Log;

import com.canshipy.youcanpaymentandroidsdk.YoucanPayment;
import com.canshipy.youcanpaymentandroidsdk.instrafaces.PayCallBack;
import com.canshipy.youcanpaymentandroidsdk.task.PayTask;

import okhttp3.FormBody;
import okhttp3.RequestBody;

import static com.canshipy.youcanpaymentandroidsdk.config.Config.PUB_KEY;
import static com.canshipy.youcanpaymentandroidsdk.config.Config.URL_PAY;

public class Pay {

    String payUrl = URL_PAY;
    CardInformation cardInformation;
    Token token;

    public Pay(){}

    public Pay setCardInformation(CardInformation cardInformation) {
        this.cardInformation = cardInformation;

        return this;
    }

    public Pay setPayUrl(String payUrl) {
        this.payUrl = payUrl;

        return this;
    }

    public Pay setToken(Token token) {
        this.token = token;

        return this;
    }

    public Pay setListener(PayCallBack payListener) {
        YoucanPayment.payListener = payListener;

        return this;
    }

    public void call() {
        if(token == null) {
            Log.e("result", "Token: Token is null");

            return;
        }

        if(YoucanPayment.payListener == null) {
            Log.e("result", "call: listener null");

            return;
        }

         RequestBody form = new FormBody.Builder()
                .add("card_holder_name", this.cardInformation.getCardHolderName())
                .add("cvv", this.cardInformation.getCvv())
                .add("credit_card", this.cardInformation.getCardNumber())
                .add("expire_date", this.cardInformation.getExpireDate())
                .add("token_id", token.id)
                .add("pub_key", PUB_KEY)
                .add("is_mobile", "1")
                .build();

        new PayTask(this.payUrl,form, YoucanPayment.payListener).execute("");
    }
}
