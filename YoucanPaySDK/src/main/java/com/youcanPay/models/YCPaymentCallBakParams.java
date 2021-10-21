package com.youcanPay.models;

import java.util.HashMap;

public class YCPaymentCallBakParams {

    private HashMap<String, String> header;

    public YCPaymentCallBakParams(HashMap<String, String> header) {
        this.header = header;
    }

    public HashMap<String, String> getHeader() {
        return header;
    }

    public void setHeader(HashMap<String, String> header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return "YCPayBalanceCallBakParams{" +
                "header=" + header +
                '}';
    }
}
