package com.youcanPay.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class YCPayResponse {
    public boolean success = false;
    @SerializedName("message")
    public String message = "";
    @SerializedName("transaction_id")
    public String transactionId = "";
    @SerializedName("return_url")
    public String returnUrl = "";
    @SerializedName("redirect_url")
    public String redirectUrl = "";

    public YCPayResponse resultFromJson(String json) throws Exception {
        YCPayResponse result;

        try {
            Gson gson = new Gson();
            result = gson.fromJson(json, YCPayResponse.class);
        } catch (Exception e) {
            throw new Exception("Invalid response");
        }

        return result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", callBackUrl='" + returnUrl + '\'' +
                ", redirectUrl='" + redirectUrl + '\'' +
                '}';
    }
}
