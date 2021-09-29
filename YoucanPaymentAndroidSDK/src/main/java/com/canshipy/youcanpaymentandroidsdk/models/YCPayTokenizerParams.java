package com.canshipy.youcanpaymentandroidsdk.models;

import com.canshipy.youcanpaymentandroidsdk.config.Config;

import java.util.HashMap;
public class YCPayTokenizerParams {

    private String tokenizerUrl;
    private String currency = Config.CURRENCY;
    private double amount;
    private HashMap<String, String> header = new HashMap<>();

    /**
     *
     * @param tokenizerUrl tokenizer Url test
     * @param amount
     */
    public YCPayTokenizerParams(String tokenizerUrl, double amount) {
        this.tokenizerUrl = tokenizerUrl;
        this.amount = amount;
    }

    public YCPayTokenizerParams(String tokenizerUrl, double amount, String currency) {
        this.tokenizerUrl = tokenizerUrl;
        this.currency = currency;
        this.amount = amount;
    }

    public YCPayTokenizerParams(String tokenizerUrl, double amount, HashMap<String, String> header) {
        this.tokenizerUrl = tokenizerUrl;
        this.amount = amount;
        this.header = header;
    }

    public YCPayTokenizerParams(String tokenizerUrl, double amount, String currency, HashMap<String, String> header) {
        this.tokenizerUrl = tokenizerUrl;
        this.currency = currency;
        this.amount = amount;
        this.header = header;
    }

    public YCPayTokenizerParams setHeader(HashMap<String, String> header) {
        this.header = header;
        return this;
    }

    public String getTokenizerUrl() {
        return tokenizerUrl;
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
                "tokenizerUrl='" + tokenizerUrl + '\'' +
                ", currency='" + currency + '\'' +
                ", amount=" + amount +
                '}';
    }
}
