package com.youcan.payment.services;

import com.youcan.payment.instrafaces.YCPayTokenizerCallBackImpl;
import com.youcan.payment.models.YCPayTokenizerParams;
import com.youcan.payment.task.YCPayTokenizerTask;

import java.net.URL;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class YCPayTokenizer {

    /**
     * This method for  init and create YCPayTokenizer
     *
     * @param tokenizerUrl your tokenizer url server
     * @param params       Tokenizer parameters
     * @param onResult     callback result
     */
    public void create(URL tokenizerUrl, YCPayTokenizerParams params, YCPayTokenizerCallBackImpl onResult) {
        RequestBody form = new FormBody.Builder()
                .add("amount", params.getAmount() + "")
                .add("currency", params.getCurrency())
                .build();

        new YCPayTokenizerTask(tokenizerUrl.toString(), params, form, onResult).execute("");
    }
}
