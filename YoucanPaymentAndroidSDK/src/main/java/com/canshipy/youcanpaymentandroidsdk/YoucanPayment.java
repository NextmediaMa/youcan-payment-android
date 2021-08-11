package com.canshipy.youcanpaymentandroidsdk;


import android.util.Log;

import com.canshipy.youcanpaymentandroidsdk.instrafaces.PayCallBack;
import com.canshipy.youcanpaymentandroidsdk.instrafaces.TokenizationCallBack;
import com.canshipy.youcanpaymentandroidsdk.models.Initilaze;
import com.canshipy.youcanpaymentandroidsdk.models.Pay;
import com.canshipy.youcanpaymentandroidsdk.models.Result;
import com.canshipy.youcanpaymentandroidsdk.models.Token;

public class YoucanPayment implements TokenizationCallBack, PayCallBack {

    public Initilaze initilaze = new Initilaze(this);
    public Pay pay = new Pay();

    @Override
    public void onResponse(Token token) {
        Log.e("build_test", token.toString() );
        this.pay.setToken(token);
    }

    @Override
    public void onError(String response) {

    }

    @Override
    public void onPaySuccess(Result response) {

    }

    @Override
    public void onPayField(String response) {

    }
}
