package com.youcan.payment.models;

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
}
