package com.canshipy.youcanpaymentandroidsdk.models;

import com.canshipy.youcanpaymentandroidsdk.instrafaces.YCPayTokenizerCallBack;
import com.canshipy.youcanpaymentandroidsdk.task.YCPayTokenizerTask;


import okhttp3.FormBody;
import okhttp3.RequestBody;

public class YCPayTokenizer {

    YCPayTokenizerCallBack listener;

    public YCPayTokenizer(YCPayTokenizerCallBack listener) {
        this.listener = listener;
    }

    public void create(YCPayTokenizerParams params) {
        RequestBody form = new FormBody.Builder()
                .add("amount", params.getAmount() + "")
                .add("currency", params.getCurrency())
                .build();

        new YCPayTokenizerTask(params, form, listener).execute("");
    }
}
