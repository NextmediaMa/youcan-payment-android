package com.youcanPay.models;

public class YCPResponseSale extends YCPayResponse {
    private final boolean success;
    private String message = "";
    private final int code;

    public YCPResponseSale(
            String transactionId,
            boolean success,
            String message,
            int code
    ) {
        super(transactionId);
        this.success = success;
        this.message = message;
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
