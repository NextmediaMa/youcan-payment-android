package com.youcan.payment.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class YCPayToken {

    @SerializedName("transaction_id")
    private String transactionId = "";
    private String id = "";
    @SerializedName("updated_at")
    private String updatedAt = "";
    @SerializedName("created_at")
    private String createdAt = "";
    private String pubKey = "";

    public YCPayToken() {
    }

    public YCPayToken(String transactionId, String pubKey) {
        this.transactionId = transactionId;
        this.pubKey = pubKey;
    }

    public YCPayToken tokenFromJson(String json) {
        Gson gson = new Gson();
        YCPayToken token;
        try {
            token = gson.fromJson(json, YCPayToken.class);
        } catch (Exception e) {
            e.printStackTrace();
            token = new YCPayToken();
        }
        return token;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getId() {
        return id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    @Override
    public String toString() {
        return "YCPayToken{" +
                "transactionId='" + transactionId + '\'' +
                ", id='" + id + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", pubKey='" + pubKey + '\'' +
                '}';
    }
}
