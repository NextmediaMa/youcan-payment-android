package com.youcanPay.models;

public class YCPayResponse {
    private String transactionId = "";

    public YCPayResponse(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
