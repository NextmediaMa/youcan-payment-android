package com.youcanPay.models;

public class YCPResponse3ds extends YCPayResponse {
    private String redirectUrl = "";
    private String returnUrl = "";

    public YCPResponse3ds(String transactionId, String redirectUrl, String returnUrl) {
        super(transactionId);
        this.redirectUrl = redirectUrl;
        this.returnUrl = returnUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }
}
