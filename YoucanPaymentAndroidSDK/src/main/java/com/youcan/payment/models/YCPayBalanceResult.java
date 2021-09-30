package com.youcan.payment.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class YCPayBalanceResult {

    private String id;
    @SerializedName("order_id")
    private String orderId;
    private String amount;
    private String currency;
    private int status;
    @SerializedName("created_at")
    private String createdAt;
    private String messageError = "";
    public String getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public int getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getMessageError(){
        return messageError;
    }
    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }

    public YCPayBalanceResult resultFromJson(String json) {
        Gson gson = new Gson();
        YCPayBalanceResult result;
        try {
            result = gson.fromJson(json, YCPayBalanceResult.class);
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }


    @Override
    public String toString() {
        return "YCPayBalanceResult{" +
                "id='" + id + '\'' +
                ", orderId='" + orderId + '\'' +
                ", amount='" + amount + '\'' +
                ", currency='" + currency + '\'' +
                ", status=" + status +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
