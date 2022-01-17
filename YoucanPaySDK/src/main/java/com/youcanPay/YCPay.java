package com.youcanPay;

import android.content.Context;

import com.youcanPay.exception.YCPayInvalidArgumentException;
import com.youcanPay.exception.YCPayInvalidPaymentMethodException;
import com.youcanPay.interfaces.CashPlusCallbackImpl;
import com.youcanPay.interfaces.PayCallbackImpl;
import com.youcanPay.models.YCPayCardInformation;
import com.youcanPay.networking.YCPayRetrofitHTTPAdapter;
import com.youcanPay.services.YCPayApiService;
import com.youcanPay.services.YCPayCashPlusService;
import com.youcanPay.services.YCPayGetConfigService;

import static com.youcanPay.utils.YCPayLocale.setLocale;

public class YCPay {
    private final String pubKey;
    private final Context context;
    private YCPayApiService apiService = new YCPayApiService(new YCPayRetrofitHTTPAdapter());
    private YCPayCashPlusService cashPushService = new YCPayCashPlusService(new YCPayRetrofitHTTPAdapter());
    private YCPayGetConfigService ycPayGetConfigService = new YCPayGetConfigService(new YCPayRetrofitHTTPAdapter());

    public YCPay(
            Context context,
            String pubKey
    ) {
        this.pubKey = pubKey;
        this.context = context;
        this.getAccountConfig();
    }

    public YCPay(
            Context context,
            String pubKey,
            String locale
    ) {
        this.pubKey = pubKey;
        this.context = context;
        this.getAccountConfig();
        setLocale(locale);
    }

    /**
     * config SandBox mode
     *
     * @param isSandboxMode SandBox status
     */
    public void setSandboxMode(boolean isSandboxMode) {
        this.apiService.setSandBoxMode(isSandboxMode);
    }

    public void payWithCard(
            String tokenId,
            YCPayCardInformation cardInformation,
            PayCallbackImpl payCallBack
    ) throws YCPayInvalidArgumentException, NullPointerException, YCPayInvalidPaymentMethodException {
        if (!this.ycPayGetConfigService.ycPayAccountConfig.isAcceptsCashPlus()) {
            throw new YCPayInvalidPaymentMethodException("Payment with Credit Card is Invalid for your account");
        }

        this.apiService.payWithCard(
                this.context,
                this.pubKey,
                tokenId,
                cardInformation,
                payCallBack
        );
    }

    public void payWithCashPlus(
            String tokenId,
            CashPlusCallbackImpl cashPlusCallback
    ) throws YCPayInvalidArgumentException, NullPointerException, YCPayInvalidPaymentMethodException {
        if (!this.ycPayGetConfigService.ycPayAccountConfig.isAcceptsCashPlus()) {
            throw new YCPayInvalidPaymentMethodException("Payment with CashPlus is Invalid for your account");
        }

        this.cashPushService.payWithCashPlus(
                this.pubKey,
                tokenId,
                cashPlusCallback
        );
    }

    private void getAccountConfig() {
        try {
            this.ycPayGetConfigService.getAccountConfig(pubKey);
        } catch (YCPayInvalidArgumentException e) {
            e.printStackTrace();
        }
    }
}
