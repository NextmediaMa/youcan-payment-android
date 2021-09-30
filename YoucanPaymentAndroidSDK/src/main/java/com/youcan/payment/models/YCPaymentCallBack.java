package com.youcan.payment.models;

import com.youcan.payment.instrafaces.YCPaymentCallBackImpl;
import com.youcan.payment.task.YCPaymentCallBackTask;

public class YCPaymentCallBack {

    YCPaymentCallBakParams params;
    String balanceCallUrl = "";


    public void create(String balanceCallUrl, YCPaymentCallBakParams params) {
        this.params = params;
        this.balanceCallUrl = balanceCallUrl;
    }

    public void call(String transactionId, YCPaymentCallBackImpl onResult) {
        new YCPaymentCallBackTask(balanceCallUrl,transactionId, params, onResult).execute("");
    }
}
