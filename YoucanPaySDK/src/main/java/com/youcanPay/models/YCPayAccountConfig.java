package com.youcanPay.models;

public class YCPayAccountConfig {
    private boolean success;
    private boolean acceptsCreditCards;
    private boolean acceptsCashPlus;
    private boolean cashPlusTransactionEnabled;
    private String message = "";

    public YCPayAccountConfig() {
    }

    public YCPayAccountConfig(
            boolean acceptsCreditCards,
            boolean acceptsCashPlus,
            boolean cashPlusTransactionEnabled
    ) {
        this.acceptsCreditCards = acceptsCreditCards;
        this.acceptsCashPlus = acceptsCashPlus;
        this.cashPlusTransactionEnabled = cashPlusTransactionEnabled;
    }

    public boolean isAcceptsCreditCards() {
        return this.acceptsCreditCards;
    }

    public void setAcceptsCreditCards(boolean acceptsCreditCards) {
        this.acceptsCreditCards = acceptsCreditCards;
    }

    public boolean isAcceptsCashPlus() {
        return this.acceptsCashPlus;
    }

    public void setAcceptsCashPlus(boolean acceptsCashPlus) {
        this.acceptsCashPlus = acceptsCashPlus;
    }

    public boolean isCashPlusTransactionEnabled() {
        return this.cashPlusTransactionEnabled;
    }

    public void setCashPlusTransactionEnabled(boolean cashPlusTransactionEnabled) {
        this.cashPlusTransactionEnabled = cashPlusTransactionEnabled;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "YCPayAccountConfig{" +
                "success=" + success +
                ", acceptsCreditCards=" + acceptsCreditCards +
                ", acceptsCashPlus=" + acceptsCashPlus +
                ", cashPlusTransactionEnabled=" + cashPlusTransactionEnabled +
                ", message='" + message + '\'' +
                '}';
    }
}
