package com.youcanPay.factories;

import android.util.Log;

import com.youcanPay.exception.YCPayInvalidDecodedJSONException;
import com.youcanPay.exception.YCPayInvalidResponseException;
import com.youcanPay.models.YCPResponse3ds;
import com.youcanPay.models.YCPResponseCashPlus;
import com.youcanPay.models.YCPResponseSale;
import com.youcanPay.models.YCPayAccountConfig;
import com.youcanPay.models.YCPayResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class YCPResponseFactory {
    public static YCPayResponse getResponse(String json) throws YCPayInvalidResponseException, YCPayInvalidDecodedJSONException {
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(json);

            if (jsonObject.has("success")) {
                return new YCPResponseSale(
                        jsonObject.has("transaction_id") ?
                                jsonObject.getString("transaction_id") : "",
                        jsonObject.getBoolean("success"),
                        jsonObject.has("message") ?
                                jsonObject.getString("message") : "",
                        jsonObject.has("code") ?
                                jsonObject.getInt("code") : null
                );
            }

            if (jsonObject.has("redirect_url") && jsonObject.has("return_url")) {
                return new YCPResponse3ds(
                        jsonObject.has("transaction_id") ? jsonObject.getString(
                                "transaction_id") : "",
                        jsonObject.getString(
                                "redirect_url"),
                        jsonObject.getString(
                                "return_url")
                );
            }

            if (jsonObject.has("token")) {
                return new YCPResponseCashPlus(
                        jsonObject.has("transaction_id") ? jsonObject.getString(
                                "transaction_id") : "",
                        jsonObject.getString("token")
                );
            }

        } catch (JSONException e) {
            throw new YCPayInvalidDecodedJSONException();
        }

        throw new YCPayInvalidResponseException("Error occurred while decoding data");
    }
}
