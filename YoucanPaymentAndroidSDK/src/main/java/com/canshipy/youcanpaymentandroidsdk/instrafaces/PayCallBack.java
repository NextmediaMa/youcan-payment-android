package com.canshipy.youcanpaymentandroidsdk.instrafaces;

public interface PayCallBack {
    void onPaySuccess(String response);
    void onPaySuccessWith3DS(String response);
    void onPayField(String response);
}
