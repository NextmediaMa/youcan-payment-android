package com.youcanPay.models;

public class YCPayToken {

    private String transactionId = "";
    private String id = "";
    private String pubKey = "";

    public YCPayToken(String transactionId, String pubKey) {
        this.transactionId = transactionId;
        this.pubKey = pubKey;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getId() {
        return id;
    }

    public String getPubKey() {
        return pubKey;
    }

    @Override
    public String toString() {
        return "YCPayToken{" +
                "transactionId='" + transactionId + '\'' +
                ", id='" + id + '\'' +
                ", pubKey='" + pubKey + '\'' +
                '}';
    }
}
