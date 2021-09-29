package com.youcan.payment.models;

import com.youcan.payment.instrafaces.YCPayTokenizerCallBackImpl;
import com.youcan.payment.task.YCPayTokenizerTask;


import okhttp3.FormBody;
import okhttp3.RequestBody;

public class YCPayTokenizer {

    YCPayTokenizerCallBackImpl listener;
    
    public YCPayTokenizer(YCPayTokenizerCallBackImpl listener) {
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
