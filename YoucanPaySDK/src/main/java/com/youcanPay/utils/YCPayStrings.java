package com.youcanPay.utils;

import java.util.HashMap;
import java.util.Map;

public class YCPayStrings {
    private static final Map<String, String> english = new HashMap<String, String>() {{
        put("unexpected_error_occurred", "An error occurred while processing the payment.");
        put("payment_canceled", "Payment canceled.");
    }};

    private static final Map<String, String> french = new HashMap<String, String>() {{
        put("unexpected_error_occurred", "Une erreur s’est produite lors du traitement du paiement.");
        put("payment_canceled", "Paiement annulé.");
    }};

    private static final Map<String, String> arabic = new HashMap<String, String>() {{
        put("unexpected_error_occurred", "حدث خطأ أثناء الدفع.");
        put("payment_canceled", "تم إلغاء الدفع.");
    }};

    public static String get(String id, String local) {
        if (local.equals("fr")) {
            return french.get(id);
        }

        if (local.equals("ar")) {
            return arabic.get(id);
        }

        return english.get(id);
    }
}
