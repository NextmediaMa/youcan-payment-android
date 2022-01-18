package com.youcanPay.exception;

import org.json.JSONException;

public class YCPayInvalidDecodedJSONException extends JSONException {
    public YCPayInvalidDecodedJSONException() {
        super("Invalid JSON Exception");
    }

    public YCPayInvalidDecodedJSONException(String message) {
        super(message);
    }
}
