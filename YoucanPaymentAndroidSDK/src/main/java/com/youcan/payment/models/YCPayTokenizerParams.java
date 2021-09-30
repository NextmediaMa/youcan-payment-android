package com.youcan.payment.models;

import com.youcan.payment.config.Config;

import java.util.HashMap;
public class YCPayTokenizerParams {

    private String currency = Config.CURRENCY;
    private double amount;
    private HashMap<String, String> header = new HashMap<>();

    /**
     *
     * @param amount
     */
    public YCPayTokenizerParams(double amount) {
      //  this.tokenizerUrl = tokenizerUrl;
        this.amount = amount;
    }

    public YCPayTokenizerParams(double amount, String currency) {
        this.currency = currency;
        this.amount = amount;
    }

    public YCPayTokenizerParams(double amount, HashMap<String, String> header) {
        this.amount = amount;
        this.header = header;
    }

    public YCPayTokenizerParams(double amount, String currency, HashMap<String, String> header) {
        this.currency = currency;
        this.amount = amount;
        this.header = header;
    }

    public YCPayTokenizerParams setHeader(HashMap<String, String> header) {
        this.header = header;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public String getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount;
    }

    public HashMap<String, String> getHeader() {
        return header;
    }

    @Override
    public String toString() {
        return "TokenizerParams{" +
                "currency='" + currency + '\'' +
                ", amount=" + amount +
                '}';
    }
}
