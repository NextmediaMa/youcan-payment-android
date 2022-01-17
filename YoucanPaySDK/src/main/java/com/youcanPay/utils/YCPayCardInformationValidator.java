package com.youcanPay.utils;

import android.text.TextUtils;

import com.youcanPay.exception.YCPayInvalidArgumentException;
import com.youcanPay.models.YCPayCardInformation;

import java.util.Calendar;
import java.util.Date;

public class YCPayCardInformationValidator {
    /**
     * check if card is valid
     *
     * @param cardInformation card information
     * @throws YCPayInvalidArgumentException
     */
    static public void validate(YCPayCardInformation cardInformation) throws YCPayInvalidArgumentException {
        if (cardInformation.getCardHolderName().equals("")) {
            throw new YCPayInvalidArgumentException("Name holder required");
        }

        if (!TextUtils.isDigitsOnly(cardInformation.getCardNumber())) {
            throw new YCPayInvalidArgumentException("Card Number must be Number");
        }

        if (!TextUtils.isDigitsOnly(cardInformation.getCvv())) {
            throw new YCPayInvalidArgumentException("CVV must be Number");
        }

        if (!isDateValid(cardInformation.getExpireDateMonth(), cardInformation.getExpireDateYear())) {
            throw new YCPayInvalidArgumentException("Date Invalid");
        }
    }

    static private boolean isDateValid(String expireDateMonth, String expireDateYear) throws YCPayInvalidArgumentException {
        int expireMonth = 0;
        int expireYears = 0;

        try {
            expireMonth = Integer.parseInt(expireDateMonth);
            expireYears = Integer.parseInt(expireDateYear);
        } catch (NumberFormatException e) {
            throw new YCPayInvalidArgumentException("Date Invalid");
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
