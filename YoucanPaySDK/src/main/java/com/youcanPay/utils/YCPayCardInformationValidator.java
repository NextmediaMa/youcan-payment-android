package com.youcanPay.utils;

import android.text.TextUtils;

import com.youcanPay.exception.YCPayInvalidArgumentException;
import com.youcanPay.models.YCPayCardInformation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        if (!isDateValid(cardInformation.getExpireDate())) {
            throw new YCPayInvalidArgumentException("Date invalid");
        }
    }

    static private boolean isDateValid(String expireDate) throws YCPayInvalidArgumentException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
        try {
            Date expire = simpleDateFormat.parse(expireDate);
            Date now = new Date();

            if(now.getMonth() == expire.getMonth() && now.getYear() == expire.getYear() ) {
                return true;
            }

            if (expire.before(new Date())) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }

        return true;
    }
}
