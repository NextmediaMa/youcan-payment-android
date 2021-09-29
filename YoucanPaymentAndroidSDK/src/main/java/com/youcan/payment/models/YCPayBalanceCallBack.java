package com.youcan.payment.models;

import com.youcan.payment.YCPay;
import com.youcan.payment.instrafaces.YCPayBalanceCallBackImpl;
import com.youcan.payment.instrafaces.YCPayTokenizerCallBackImpl;
import com.youcan.payment.task.YCPayBalanceCallBackTask;
import com.youcan.payment.task.YCPayTokenizerTask;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class YCPayBalanceCallBack {

    YCPayBalanceCallBackImpl listener;

    public void setListener(YCPayBalanceCallBackImpl listener) {
        this.listener = listener;
    }

    public void create(YCPayBalanceCallBakParams params) {
        params.setTransactionId(YCPay.pay.getToken().getTransactionId());

        RequestBody form = new FormBody.Builder()
                .add("transaction_id", params.getTransactionId())
                .build();

        new YCPayBalanceCallBackTask(params, form, listener).execute("");
    }
}
