package com.canshipy.youcanpaymentandroidsdk;


import android.util.Log;

import com.canshipy.youcanpaymentandroidsdk.instrafaces.PayCallBack;
import com.canshipy.youcanpaymentandroidsdk.instrafaces.TokenizationCallBack;
import com.canshipy.youcanpaymentandroidsdk.models.Initilaze;
import com.canshipy.youcanpaymentandroidsdk.models.Pay;
import com.canshipy.youcanpaymentandroidsdk.models.Result;
import com.canshipy.youcanpaymentandroidsdk.models.Token;
import com.canshipy.youcanpaymentandroidsdk.task.TestLoadHtml;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class YoucanPayment implements  PayCallBack {

    static public Pay pay = new Pay();
    static TokenizationCallBack listener = new TokenizationCallBack() {
        @Override
        public void onResponse(Token token) {
            pay.setToken(token);
        }

        @Override
        public void onError(String response) {
            Log.e("build_test", response );
        }
    };

    static public Initilaze initilaze = new Initilaze(listener);

    static public void loadHtml(PayCallBack listener){
        RequestBody form = new FormBody.Builder()
                .add("PaReq", "")
                .add("MD", "")
                .add("TermUrl", "")
                .build();
        new TestLoadHtml("https://api.payzone.ma/mpi/pareq/104910590",form,listener).execute("");
    }

    @Override
    public void onPaySuccess(Result response) {

    }

    @Override
    public void onPayFailure(String response) {

    }
}
