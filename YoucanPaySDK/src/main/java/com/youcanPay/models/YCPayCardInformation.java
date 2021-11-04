package com.youcanPay.models;

import com.youcanPay.utils.YCPayCardInformationValidator;

import java.util.HashMap;

public class YCPayCardInformation {
    private String cardHolderName = "";
    private String cardNumber = "";
    private String expireDateYear = "";
    private String expireDateMonth = "";
    private String cvv = "";

    public YCPayCardInformation(String cardHolderName, String cardNumber, String expireDateMonth, String expireDateYear, String cvv) throws Exception {
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.expireDateMonth = expireDateMonth;
        this.expireDateYear = expireDateYear;
        this.cvv = cvv;

        YCPayCardInformationValidator.validate(this);
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpireDateYear() {
        return expireDateYear;
    }

    public String getExpireDateMonth() {
        return expireDateMonth;
    }

    public String getExpireDate() {
        return expireDateMonth + "/" + expireDateYear;
    }

    public String getCvv() {
        return cvv;
    }

    /**
     * convert card information to key, values
     *
     * @return HashMap<String, String>
     */
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("card_holder_name", this.getCardHolderName());
        hashMap.put("cvv", this.getCvv());
        hashMap.put("credit_card", this.getCardNumber());
        hashMap.put("expire_date", this.getExpireDate());

        return hashMap;
    }
}
