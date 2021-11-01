package com.youcanPay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.youcanPay.interfaces.PayCallbackImpl;
import com.youcanPay.interfaces.YCPayWebViewCallbackImpl;
import com.youcanPay.models.YCPayResponse;
import com.youcanPay.utils.Strings;

import java.util.HashMap;
import java.util.regex.Pattern;

import static com.youcanPay.config.YCPayConfig.YCP_TAG;
import static com.youcanPay.config.YCPayConfig.locale;

public class YCPayWebView extends WebView {
    private String returnUrl = "";
    private PayCallbackImpl webViewListener;
    private YCPayWebViewCallbackImpl onFinishCallback;

    public void setWebViewListener(PayCallbackImpl webViewListener) {
        this.webViewListener = webViewListener;
    }

    public void setOnFinishCallback(YCPayWebViewCallbackImpl onFinishCallback) {
        this.onFinishCallback = onFinishCallback;
    }

    public YCPayWebView(@NonNull Context context) {
        super(context);
    }

    public YCPayWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        initWebViewClient();
    }

    private void initWebViewClient() {
        this.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (webViewListener == null) {
                    return;
                }

                Log.e(YCP_TAG, "onPageFinished: " + url);
                try {
                    if (url.contains(returnUrl) && url.contains("is_success=0")) {
                        HashMap<String, String> urlData = getListenUrlResult(url);

                        webViewListener.onFailure(urlData.get("message"));
                        onFinishCallback.onFinish();
                        return;
                    }

                    if (url.contains(returnUrl) && url.contains("is_success=1")) {
                        HashMap<String, String> urlData = getListenUrlResult(url);

                        webViewListener.onSuccess(urlData.get("transaction_id"));
                        onFinishCallback.onFinish();
                    }

                } catch (Exception exception) {
                    exception.printStackTrace();

                    webViewListener.onFailure(Strings.get("unexpected_error_occurred", locale));
                    onFinishCallback.onFinish();
                }
            }
        });
    }

    public YCPayWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void loadResult(YCPayResponse result) {
        this.returnUrl = result.returnUrl;

        this.loadUrl(result.redirectUrl);
    }

    private HashMap<String, String> getListenUrlResult(String url) {
        String[] urlSplit = url.split(Pattern.quote("?"));

        if (urlSplit.length == 1) {
            return new HashMap<>();
        }

        String[] data = urlSplit[1].split("&");
        HashMap<String, String> hash = new HashMap<>();

        for (String datum : data) {
            if (datum.split("=").length > 1) {
                hash.put(datum.split("=")[0], datum.split("=")[1].replace("+", " "));
            }

            if (datum.split("=").length == 1) {
                hash.put(datum.split("=")[0], "");
            }
        }

        return hash;
    }

}
