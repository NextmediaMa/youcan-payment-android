package com.canshipy.youcanpaymentandroidsdk.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Result {

    public boolean success = false;
    public String message = "";
    public boolean is3DS = false;
    public String threeDsPage = "";
    @SerializedName("PaReq")
    public String paReq = "";
    @SerializedName("transaction_id")
    public String transactionId = "";
    public String callBackUrl = "";
    public boolean callBackInvoked = false;

    public Result() {
    }
    public Result(boolean success, String message, boolean is3DS, String threeDsPage, String transactionId, String callBackUrl, boolean callBackInvoked) {
        this.success = success;
        this.message = message;
        this.is3DS = is3DS;
        this.threeDsPage = threeDsPage;
        this.transactionId = transactionId;
        this.callBackUrl = callBackUrl;
        this.callBackInvoked = callBackInvoked;
    }

    public Result resultFromJson(String json){
        Gson gson = new Gson();
        Result result;
        try {
            result = gson.fromJson(json, Result.class);
        } catch (Exception e){
            e.printStackTrace();
            result = new Result();
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
                ", transactionId='" + transactionId + '\'' +
                ", callBackUrl='" + callBackUrl + '\'' +
                ", callBackInvoked=" + callBackInvoked +
                '}';
    }
}
