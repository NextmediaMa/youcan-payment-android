package com.canshipy.youcanpaymentandroidsdk.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("transaction_id")
    String transactionId = "";
    String id = "";
    @SerializedName("updated_at")
    String updatedAt = "";
    @SerializedName("created_at")
    String createdAt = "";

    public Token() {
    }

    public Token(String transactionId, String id, String updatedAt, String createdAt) {
        this.transactionId = transactionId;
        this.id = id;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public Token tokenFromJson(String json){
        Gson gson = new Gson();
        Token token;
        try {
            token = gson.fromJson(json, Token.class);
        } catch (Exception e){
            e.printStackTrace();
            token = new Token();
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
