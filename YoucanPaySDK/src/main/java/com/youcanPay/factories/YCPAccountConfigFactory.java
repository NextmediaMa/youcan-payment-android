package com.youcanPay.factories;

import android.util.Log;

import com.youcanPay.exception.YCPayInvalidDecodedJSONException;
import com.youcanPay.exception.YCPayInvalidResponseException;
import com.youcanPay.models.YCPResponse3ds;
import com.youcanPay.models.YCPResponseSale;
import com.youcanPay.models.YCPayAccountConfig;
import com.youcanPay.models.YCPayResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class YCPAccountConfigFactory {
    public static YCPayAccountConfig getResponse(String json) throws YCPayInvalidResponseException,
            YCPayInvalidDecodedJSONException {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(json);

            if (jsonObject.has("acceptsCreditCards") && jsonObject.has("acceptsCashPlus") && jsonObject.has("cashPlusTransactionEnabled")) {
                YCPayAccountConfig ycPayAccountConfig = new YCPayAccountConfig(
                        jsonObject.getBoolean(
                                "acceptsCreditCards"),
                        jsonObject.getBoolean(
                                "acceptsCashPlus"),
                        jsonObject.getBoolean(
                                "cashPlusTransactionEnabled")
                );
                ycPayAccountConfig.setSuccess(true);

                return ycPayAccountConfig;
            }

            if (jsonObject.has("message")) {
                YCPayAccountConfig ycPayAccountConfig = new YCPayAccountConfig();
                ycPayAccountConfig.setMessage(jsonObject.getString("message"));

                return ycPayAccountConfig;
            }

        } catch (JSONException e) {
            throw new YCPayInvalidDecodedJSONException();
        }

        throw new YCPayInvalidResponseException("Error occurred while decoding data");
    }
}
