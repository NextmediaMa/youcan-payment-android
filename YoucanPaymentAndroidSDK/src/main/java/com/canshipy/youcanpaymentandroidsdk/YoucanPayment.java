package com.canshipy.youcanpaymentandroidsdk;


import android.util.Log;

import com.canshipy.youcanpaymentandroidsdk.instrafaces.PayCallBack;
import com.canshipy.youcanpaymentandroidsdk.instrafaces.TokenizationCallBack;
import com.canshipy.youcanpaymentandroidsdk.models.Initilaze;
import com.canshipy.youcanpaymentandroidsdk.models.Pay;
import com.canshipy.youcanpaymentandroidsdk.models.Result;
import com.canshipy.youcanpaymentandroidsdk.models.Token;
import com.canshipy.youcanpaymentandroidsdk.task.Load3DSPage;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class YoucanPayment {

    static public Pay pay = new Pay();
    static public PayCallBack payListener;
    static TokenizationCallBack listener = new TokenizationCallBack() {
        @Override
        public void onResponse(Token token) {
            if(token!=null)
                pay.setToken(token);
        }

        @Override
        public void onError(String response) {
            Log.e("build_test", "onError: "+response );
        }
    };

    static public void setTokenId(String tokenId){
        Token token = new Token();
        token.setId(tokenId);
        pay.setToken(token);
    }

    static public void load3DsPage(Result result) {
        RequestBody form = new FormBody.Builder()
                .add("PaReq", result.paReq)
                .add("MD", result.transactionId)
                .add("TermUrl", result.callBackUrl)
                .build();

        new Load3DSPage(result.redirectUrl,result.listenUrl,form).execute("");
    }


}
