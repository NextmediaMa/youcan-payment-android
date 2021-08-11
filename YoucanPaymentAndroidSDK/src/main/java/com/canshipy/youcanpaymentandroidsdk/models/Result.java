package com.canshipy.youcanpaymentandroidsdk.models;

public class Result {

    public boolean success = false;
    public String message = "";
    public boolean is3DS = false;
    public String threeDsPage = "";
    public String transactionId = "";
    public String callBackUrl = "";
    public boolean callBackInvoked = false;

    public Result(boolean success, String message, boolean is3DS, String threeDsPage, String transactionId, String callBackUrl, boolean callBackInvoked) {
        this.success = success;
        this.message = message;
        this.is3DS = is3DS;
        this.threeDsPage = threeDsPage;
        this.transactionId = transactionId;
        this.callBackUrl = callBackUrl;
        this.callBackInvoked = callBackInvoked;
    }
}
