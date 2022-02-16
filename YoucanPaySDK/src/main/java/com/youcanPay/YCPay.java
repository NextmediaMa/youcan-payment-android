package com.youcanPay;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.youcanPay.exception.YCPayInvalidArgumentException;
import com.youcanPay.exception.YCPayInvalidPaymentMethodException;
import com.youcanPay.interfaces.CashPlusCallbackImpl;
import com.youcanPay.interfaces.PayCallbackImpl;
import com.youcanPay.models.YCPayAccountConfig;
import com.youcanPay.models.YCPayCardInformation;
import com.youcanPay.networking.YCPayRetrofitHTTPAdapter;
import com.youcanPay.services.YCPayApiService;
import com.youcanPay.services.YCPayCashPlusService;
import com.youcanPay.services.YCPayConfigService;
import com.youcanPay.utils.YCPayLocale;
import com.youcanPay.utils.YCPayStrings;

import static com.youcanPay.utils.YCPayLocale.setLocale;

public class YCPay {
    private final String pubKey;
    private final Context context;
    private YCPayApiService apiService = new YCPayApiService(new YCPayRetrofitHTTPAdapter());
    private YCPayCashPlusService cashPushService = new YCPayCashPlusService(new YCPayRetrofitHTTPAdapter());
    private YCPayConfigService ycPayGetConfigService = new YCPayConfigService(new YCPayRetrofitHTTPAdapter());
    private LiveData<Boolean> isConfigLoaded;

    public YCPay(
            Context context,
            String pubKey
    ) {
        this.pubKey = pubKey;
        this.context = context;
        this.loadAccountConfig();
    }

    public YCPay(
            Context context,
            String pubKey,
            String locale
    ) {
        this.pubKey = pubKey;
        this.context = context;
        this.loadAccountConfig();
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
        if (!this.ycPayGetConfigService.accountConfig.isAcceptsCreditCards()) {
            throw new YCPayInvalidPaymentMethodException(YCPayStrings.get(
                    "payment_with_card_invalid",
                    YCPayLocale.getLocale()
            ));
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
        if (!this.ycPayGetConfigService.accountConfig.isAcceptsCashPlus()) {
            throw new YCPayInvalidPaymentMethodException(YCPayStrings.get(
                    "payment_with_cashplus_invalid",
                    YCPayLocale.getLocale()
            ));
        }

        this.cashPushService.payWithCashPlus(
                this.pubKey,
                tokenId,
                cashPlusCallback
        );
    }

    private void loadAccountConfig() {
        try {
            this.ycPayGetConfigService.getAccountConfig(pubKey);
        } catch (YCPayInvalidArgumentException e) {
            e.printStackTrace();
        }
    }

    public LiveData<YCPayAccountConfig> getAccountConfigLiveData() {
        return this.ycPayGetConfigService.getAccountConfig();
    }

    public YCPayAccountConfig getAccountConfig() {
        return this.ycPayGetConfigService.accountConfig;
    }
}
