package com.youcan.payment.models;

import com.youcan.payment.instrafaces.YCPayTokenizerCallBack;
import com.youcan.payment.task.YCPayTokenizerTask;


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
