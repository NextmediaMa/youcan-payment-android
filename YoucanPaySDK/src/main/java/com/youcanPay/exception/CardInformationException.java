package com.youcanPay.exception;

public class CardInformationException extends Exception{
    public CardInformationException(String message) {
        super("CardInformation: "+message);
    }
}
