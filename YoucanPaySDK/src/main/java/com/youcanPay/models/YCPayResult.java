package com.youcanPay.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class YCPayResult {

    public boolean success = false;
    @SerializedName("message")
    public String message = "";
    public boolean is3DS = false;
    public String threeDsPage = "";
    @SerializedName("PaReq")
    public String paReq = "";
    @SerializedName("transaction_id")
    public String transactionId = "";
    @SerializedName("return_url")
    public String returnUrl = "";
    @SerializedName("redirect_url")
    public String redirectUrl = "";
    @SerializedName("listen_url")
    public String listenUrl = "";

    public YCPayResult resultFromJson(String json) {

        Gson gson = new Gson();
        YCPayResult result;

        try {
            result = gson.fromJson(json, YCPayResult.class);
        } catch (Exception e) {
            e.printStackTrace();
            result = new YCPayResult();
        }

        return result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", is3DS=" + is3DS +
                ", threeDsPage='" + threeDsPage + '\'' +
                ", paReq='" + paReq + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", callBackUrl='" + returnUrl + '\'' +
                ", redirectUrl='" + redirectUrl + '\'' +
                ", listenUrl='" + listenUrl + '\'' +
                '}';
    }
}