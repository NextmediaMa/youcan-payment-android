package com.youcanPay.models;

import android.text.TextUtils;

import com.youcanPay.exception.CardInformationException;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class YCPayCardInformation {
    private String cardHolderName = "";
    private String cardNumber = "";
    private String expireDateYear = "";
    private String expireDateMonth = "";
    private String cvv = "";

    public YCPayCardInformation
            (
                    String cardHolderName,
                    String cardNumber,
                    String expireDateMonth,
                    String expireDateYear,
                    String cvv
            ) throws Exception {
        isCardValid(cardHolderName, cardNumber, expireDateMonth, expireDateYear, cvv);

        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.expireDateMonth = expireDateMonth;
        this.expireDateYear = expireDateYear;
        this.cvv = cvv;
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

    public String getExpireDateDay() {
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

    /**
     * check if card is valid
     *
     */
    private void isCardValid(String cardHolderName,
                             String cardNumber,
                             String expireDateMonth,
                             String expireDateYear,
                             String cvv) throws Exception {
        if (cardHolderName.equals("")) {
            throw new CardInformationException("Name holder required");
        }

        if (!TextUtils.isDigitsOnly(cardNumber)) {
            throw new CardInformationException("Card Number must be Number");
        }

        if (!TextUtils.isDigitsOnly(cvv)) {
            throw new CardInformationException("CVV must be Number");
        }

        if (!this.isDateValid(expireDateMonth,expireDateYear)) {
            throw new CardInformationException("Date Invalid");
        }
    }

    private boolean isDateValid(String expireDateMonth, String expireDateYear) throws Exception {
        int expireMonth = 0;
        int expireYears = 0;

        try {
            expireMonth = Integer.parseInt(expireDateMonth);
            expireYears = Integer.parseInt(expireDateYear);
        } catch (Exception e) {
            throw new CardInformationException("Date Invalid");
        }

        if (expireMonth > 12 || expireMonth <= 0) {
            return false;
        }

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int years = cal.get(Calendar.YEAR) % 100;
        int month = cal.get(Calendar.MONTH);

        if (expireYears < years) {
            return false;
        }

        if (expireYears == years && expireMonth > month) {
            return false;
        }

        return true;
    }
}
