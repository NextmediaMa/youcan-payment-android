package com.youcanPay.factories;

import com.youcanPay.models.YCPResponse3ds;
import com.youcanPay.models.YCPResponseSale;
import com.youcanPay.models.YCPayResponse;

import org.json.JSONObject;

public class YCPResponseFactory {
    public static YCPayResponse getResponse(String json) throws Exception {
        JSONObject jsonObject = new JSONObject(json);

        if (jsonObject.has("success")) {
            return new YCPResponseSale(
                    jsonObject.has("transaction_id") ? jsonObject.getString(
                            "transaction_id") : "",
                    jsonObject.getBoolean("success"),
                    jsonObject.has("message") ? jsonObject.getString(
                            "message") : "",
                    jsonObject.has("code") ? jsonObject.getInt("code") : null
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

        throw new Exception("Error occurred while decoding data");
    }

}
