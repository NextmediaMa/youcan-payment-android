package com.canshipy.youcanpaymentandroidsdk.instrafaces;

import com.canshipy.youcanpaymentandroid.models.Token;

public interface PayCallBack {
    void onPaySuccess(String response);
    void onPaySuccessWith3DS(String response);
    void onPayField(String response);
}
