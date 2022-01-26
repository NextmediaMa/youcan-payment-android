package com.youcanPay.utils;

import android.text.TextUtils;
import android.util.Log;

import com.youcanPay.exception.YCPayInvalidArgumentException;
import com.youcanPay.models.YCPayCardInformation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

    static private boolean isDateValid(String expireDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
        simpleDateFormat.setLenient(false);
        try {
            Date expire = simpleDateFormat.parse(expireDate);
            Date now = new Date();

            Calendar cal = Calendar.getInstance();
            cal.setTime(now);

            int nowMonth = cal.get(Calendar.MONTH);
            int nowYear = cal.get(Calendar.YEAR);

            cal.setTime(expire);

            int expireMonth = cal.get(Calendar.MONTH);
            int expireYear = cal.get(Calendar.YEAR);

            if(nowMonth == expireMonth && nowYear == expireYear){
                return true;
            }

            if (expire.before(now)) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }

        return true;
    }
}
