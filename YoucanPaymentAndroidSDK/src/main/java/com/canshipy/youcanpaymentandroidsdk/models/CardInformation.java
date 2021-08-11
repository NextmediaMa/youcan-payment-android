package com.canshipy.youcanpaymentandroidsdk.models;

public class CardInformation {

    String cardHolderName="";
    String cardNumber="";
    String expireDate="";
    String cvv="";

    public CardInformation() {
    }

    public CardInformation(String cardHolderName, String cardNumber, String expireDate, String cvv) {
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.expireDate = expireDate;
        this.cvv = cvv;
    }
}
