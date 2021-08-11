package com.canshipy.youcanpaymentandroidsdk;


import com.canshipy.youcanpaymentandroidsdk.instrafaces.PayCallBack;
import com.canshipy.youcanpaymentandroidsdk.instrafaces.TokenizationCallBack;
import com.canshipy.youcanpaymentandroidsdk.models.Initilaze;
import com.canshipy.youcanpaymentandroidsdk.models.Pay;
import com.canshipy.youcanpaymentandroidsdk.models.Token;

public class YoucanPayment implements TokenizationCallBack, PayCallBack {

    Token token;
    public Initilaze initilaze = new Initilaze(this);
    public Pay pay = new Pay();

    @Override
    public void onResponse(Token token) {
        this.token = token;
        this.pay.setToken(token);
    }

    @Override
    public void onError(String response) {

    }


    @Override
    public void onPaySuccess(String response) {

    }

    @Override
    public void onPaySuccessWith3DS(String response) {

    }

    @Override
    public void onPayField(String response) {

    }
}
