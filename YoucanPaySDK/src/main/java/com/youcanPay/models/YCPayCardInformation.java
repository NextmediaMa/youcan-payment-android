package com.youcanPay.models;

import java.util.HashMap;

public class YCPayCardInformation {

    private String cardHolderName = "";
    private String cardNumber = "";
    private String expireDate = "";
    private String cvv = "";

    public YCPayCardInformation(String cardHolderName, String cardNumber, String expireDate, String cvv) {
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.expireDate = expireDate;
        this.cvv = cvv;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public String getCvv() {
        return cvv;
    }

    public HashMap<String, String> toHashMap() {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("card_holder_name", this.getCardHolderName());
        hashMap.put("cvv", this.getCvv());
        hashMap.put("credit_card", this.getCardNumber());
        hashMap.put("expire_date", this.getExpireDate());

        return hashMap;
    }
}
