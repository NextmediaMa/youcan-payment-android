package com.youcanPay.interfaces;

import com.youcanPay.models.HttpResponse;

public interface HttpCallBackImpl {
    void onResponse(HttpResponse response);
    void onError(String message);
}
