package com.youcan.payment.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class YCPayToken {

    @SerializedName("transaction_id")
    String transactionId = "";
    String id = "";
    @SerializedName("updated_at")
    String updatedAt = "";
    @SerializedName("created_at")
    String createdAt = "";

    public YCPayToken() {
    }

    public YCPayToken(String transactionId, String id, String updatedAt, String createdAt) {
        this.transactionId = transactionId;
        this.id = id;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
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

    @Override
    public String toString() {
        return "Token{" +
                "transactionId='" + transactionId + '\'' +
                ", id='" + id + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
