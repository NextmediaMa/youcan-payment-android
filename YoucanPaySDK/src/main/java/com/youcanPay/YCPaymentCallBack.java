package com.youcanPay;

import com.youcanPay.interfaces.YCPaymentCallBackImpl;
import com.youcanPay.models.YCPaymentCallBakParams;
import com.youcanPay.viewModel.ViewModelPaymentCallBack;

import java.net.URL;
import java.util.HashMap;

/**
 * allow you to call Payment callback
 * to notify your back end state of payment
 */

public class YCPaymentCallBack {

    private YCPaymentCallBakParams params;
    private URL balanceCallUrl;
    ViewModelPaymentCallBack viewModelPaymentCallBack;

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
     * @param transactionId     Token TransactionId
     * @param ycPaymentCallBack listener to catch result of your request
     */
    public void call(String transactionId, YCPaymentCallBackImpl ycPaymentCallBack) throws Exception {
        this.viewModelPaymentCallBack = new ViewModelPaymentCallBack(balanceCallUrl.toString());
        HashMap<String, String> body = new HashMap<String, String>() {{
            put("transaction_id", transactionId);
            put("mobile", "1");
        }};

        viewModelPaymentCallBack.callPaymentCallBAck(this.params, body, ycPaymentCallBack);
    }
}
