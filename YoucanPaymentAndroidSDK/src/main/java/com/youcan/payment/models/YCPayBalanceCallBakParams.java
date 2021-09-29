package com.youcan.payment.models;

import com.youcan.payment.YCPay;

import java.util.HashMap;

public class YCPayBalanceCallBakParams {

    private String balanceCallBackUrl;
    private String transactionId;
    private HashMap<String, String> header = new HashMap<>();

    public YCPayBalanceCallBakParams(String tokenizerUrl) {
        this.balanceCallBackUrl = tokenizerUrl;
    }

    public YCPayBalanceCallBakParams(String tokenizerUrl, HashMap<String, String> header) {
        this.balanceCallBackUrl = tokenizerUrl;
        this.header = header;
    }

    public String getBalanceCallBackUrl() {
        return balanceCallBackUrl;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public HashMap<String, String> getHeader() {
        return header;
    }

    public void setBalanceCallBackUrl(String balanceCallBackUrl) {
        this.balanceCallBackUrl = balanceCallBackUrl;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setHeader(HashMap<String, String> header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return "YCPayBalanceCallBakParams{" +
                "tokenizerUrl='" + balanceCallBackUrl + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", header=" + header +
                '}';
    }
}
