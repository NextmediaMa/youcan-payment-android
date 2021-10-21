package com.youcanPay.services;

import com.youcanPay.interfaces.YCPaymentCallBackImpl;
import com.youcanPay.models.YCPaymentCallBakParams;
import com.youcanPay.task.YCPaymentCallBackTask;

import java.net.URL;

/**
 * allow you to call Payment callback
 * to notify your back end state of payment
 */

public class YCPaymentCallBack {

    YCPaymentCallBakParams params;
    URL balanceCallUrl;

    /**
     * To initial a params of PaymentCallback
     *
     * @param balanceCallUrl url to your payCallBack server side
     * @param params         params her you can add header to your request
     */
    public void create(URL balanceCallUrl, YCPaymentCallBakParams params) {
        this.params = params;
        this.balanceCallUrl = balanceCallUrl;
    }

    /**
     * To call your payCallBack request
     *
     * @param transactionId Token TransactionId
     * @param onResult      listener to catch result of your request
     */
    public void call(String transactionId, YCPaymentCallBackImpl onResult) throws Exception {
        new YCPaymentCallBackTask(balanceCallUrl.toString(), transactionId, params, onResult).execute("");
    }
}
