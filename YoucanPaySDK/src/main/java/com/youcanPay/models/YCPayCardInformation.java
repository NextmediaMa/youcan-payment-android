package com.youcanPay.models;

import android.text.TextUtils;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.youcanPay.config.YCPayConfig.YCP_TAG;

public class YCPayCardInformation {
    private String cardHolderName = "";
    private String cardNumber = "";
    private String expireDateYears = "";
    private String expireDateMonth = "";
    private String cvv = "";

    public YCPayCardInformation(String cardHolderName, String cardNumber, String expireDateMonth, String expireDateYears, String cvv) {
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.expireDateMonth = expireDateMonth;
        this.expireDateYears = expireDateYears;
        this.cvv = cvv;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpireDateYears() {
        return expireDateYears;
    }

    public String getExpireDateDay() {
        return expireDateMonth;
    }

    public String getExpireDate() {
        return expireDateMonth + "/" + expireDateYears;
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

    public boolean isCardValid() {
        if (this.cardHolderName.equals("")) {
            return false;
        }

        if (!TextUtils.isDigitsOnly(this.cardNumber)) {
            return false;
        }

        if (!TextUtils.isDigitsOnly(this.cvv)) {
            return false;
        }

        if (!this.isDateValid()) {
            return false;
        }

        return true;
    }

    private boolean isDateValid() {
        int expireMonth = 0;
        int expireYears = 0;

        try {
            expireMonth = Integer.parseInt(this.expireDateMonth);
            expireYears = Integer.parseInt(this.expireDateYears);
        } catch (Exception e) {
            return false;
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
