package com.youcanPay.models;

public class YCPResponseCashPlus extends YCPayResponse {
    private String token = "";

    public YCPResponseCashPlus(
            String transactionId,
            String token
    ) {
        super(transactionId);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
