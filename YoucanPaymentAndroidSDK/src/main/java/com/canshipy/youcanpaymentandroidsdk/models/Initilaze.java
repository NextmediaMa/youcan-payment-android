package com.canshipy.youcanpaymentandroidsdk.models;

import com.canshipy.youcanpaymentandroidsdk.instrafaces.TokenizationCallBack;
import com.canshipy.youcanpaymentandroidsdk.task.TokenizationTask;

import okhttp3.FormBody;
import okhttp3.RequestBody;

import static com.canshipy.youcanpaymentandroidsdk.config.Config.*;

public class Initilaze {

    String tokenizationUrl = URL_TOKENIZATION;
    String currency = CURRENCY;
    String accountId = ACCOUNT_ID;
    String pubKey = PUB_KEY;
    String orderId = ORDER_ID;
    double amount = 0;
    TokenizationCallBack listener;

    public Initilaze(TokenizationCallBack listener) {
        this.listener = listener;
    }

    public Initilaze setTokenUrl(String url) {
        this.tokenizationUrl = url;

        return this;
    }

    public Initilaze setAccountId(String accountId) {
        this.accountId = accountId;

        return this;
    }

    public Initilaze setCurrency(String currency) {
        this.currency = currency;

        return this;
    }

    public Initilaze setAmount(double amount) {
        this.amount = amount;

        return this;
    }

    public Initilaze setOrderId(String orderId) {
        this.orderId = orderId;

        return this;
    }

    public Initilaze setPublicKey(String pubKey) {
        this.pubKey = pubKey;

        return this;
    }

    public void call() {
        RequestBody form = new FormBody.Builder()
                .add("amount", amount+"")
                .add("currency", currency)
                .add("pub_key", pubKey)
                .add("order_id", orderId)
                .build();

        new TokenizationTask(this.tokenizationUrl,form, listener).execute("");
    }

    @Override
    public String toString() {
        return "Initilaze{" +
                "tokenizationUrl='" + tokenizationUrl + '\'' +
                ", currency='" + currency + '\'' +
                ", accountId='" + accountId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", amount=" + amount +
                '}';
    }
}
