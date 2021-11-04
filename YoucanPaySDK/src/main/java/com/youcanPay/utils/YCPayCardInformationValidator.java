package com.youcanPay.utils;

import android.text.TextUtils;

import com.youcanPay.exception.CardInformationException;
import com.youcanPay.models.YCPayCardInformation;

import java.util.Calendar;
import java.util.Date;

public class YCPayCardInformationValidator {
    /**
     * check if card is valid
     *
     * @param cardInformation card information
     * @throws CardInformationException
     */
    static public void validate(YCPayCardInformation cardInformation) throws CardInformationException {
        if (cardInformation.getCardHolderName().equals("")) {
            throw new CardInformationException("Name holder required");
        }

        if (!TextUtils.isDigitsOnly(cardInformation.getCardNumber())) {
            throw new CardInformationException("Card Number must be Number");
        }

        if (!TextUtils.isDigitsOnly(cardInformation.getCvv())) {
            throw new CardInformationException("CVV must be Number");
        }

        if (!isDateValid(cardInformation.getExpireDateMonth(), cardInformation.getExpireDateYear())) {
            throw new CardInformationException("Date Invalid");
        }
    }

    static private boolean isDateValid(String expireDateMonth, String expireDateYear) throws CardInformationException {
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
