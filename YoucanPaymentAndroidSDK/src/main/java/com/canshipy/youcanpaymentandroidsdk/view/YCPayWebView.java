package com.canshipy.youcanpaymentandroidsdk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.canshipy.youcanpaymentandroidsdk.YCPay;
import com.canshipy.youcanpaymentandroidsdk.instrafaces.YCPayWebViewCallBack;
import com.canshipy.youcanpaymentandroidsdk.models.YCPayResult;

public class YCPayWebView extends WebView {

    private String listenerUrl;

    YCPayWebViewCallBack webViewListener;

    public void setWebViewListener(YCPayWebViewCallBack webViewListener) {
        this.webViewListener = webViewListener;
    }

    public YCPayWebView(@NonNull Context context) {
        super(context);
    }

    public YCPayWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        this.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e("build_test_1", url);

                try {
                    if (url.contains("is_success=0")) {
                        YCPay.payListener.onPayFailure("3Ds not Success");
                        if (webViewListener != null) {
                            webViewListener.onResult();
                        }
                        return;
                    }

                    if (url.contains("is_success=1")) {
                        YCPay.payListener.onPaySuccess(new YCPayResult());
                        if (webViewListener != null) {
                            webViewListener.onResult();
                        }

                        return;
                    }

                    return;
                } catch (Exception exception) {
                    exception.printStackTrace();
                    YCPay.payListener.onPayFailure("3Ds View Page: error has occurred");
                    if (webViewListener != null) {
                        webViewListener.onResult();
                    }

                    return;
                }
            }
        });
    }

    public YCPayWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void loadResult(YCPayResult result) {
        this.listenerUrl = result.listenUrl;
        this.loadDataWithBaseURL("", result.threeDsPage, "text/html", "utf-8", null);
    }

}
