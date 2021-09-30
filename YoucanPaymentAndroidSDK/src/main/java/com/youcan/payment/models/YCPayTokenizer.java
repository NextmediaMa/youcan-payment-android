package com.youcan.payment.models;

import com.youcan.payment.instrafaces.YCPayTokenizerCallBackImpl;
import com.youcan.payment.task.YCPayTokenizerTask;


import okhttp3.FormBody;
import okhttp3.RequestBody;

public class YCPayTokenizer {

    /**
     * this method for  init and create YCPayTokenizer
     * @param tokenizerUrl your tokenizer url server
     * @param params Tokenizer parameters
     * @param onResult callback result
     */
    public void create(String tokenizerUrl, YCPayTokenizerParams params,YCPayTokenizerCallBackImpl onResult) {
        RequestBody form = new FormBody.Builder()
                .add("amount", params.getAmount() + "")
                .add("currency", params.getCurrency())
                .build();

        new YCPayTokenizerTask(tokenizerUrl,params, form, onResult).execute("");
    }
}
