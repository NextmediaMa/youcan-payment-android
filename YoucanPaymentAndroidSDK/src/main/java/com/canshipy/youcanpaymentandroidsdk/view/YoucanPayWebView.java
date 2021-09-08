package com.canshipy.youcanpaymentandroidsdk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.canshipy.youcanpaymentandroidsdk.YoucanPayment;
import com.canshipy.youcanpaymentandroidsdk.instrafaces.WebViewCallBack;
import com.canshipy.youcanpaymentandroidsdk.models.Result;

public class YoucanPayWebView extends WebView {

    private String listenerUrl;

    WebViewCallBack webViewListener;

    public void setWebViewListener(WebViewCallBack webViewListener) {
        this.webViewListener = webViewListener;
    }

    public YoucanPayWebView(@NonNull Context context) {
        super(context);
    }

    public YoucanPayWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
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
                        YoucanPayment.payListener.onPayFailure("3Ds not Success");
                        if (webViewListener != null) {
                            webViewListener.onResult();
                        }
                        return;
                    }

                    if (url.contains("is_success=1")) {
                        YoucanPayment.payListener.onPaySuccess(new Result());
                        if (webViewListener != null) {
                            webViewListener.onResult();
                        }

                        return;
                    }

                    return;
                } catch (Exception exception) {
                    exception.printStackTrace();
                    YoucanPayment.payListener.onPayFailure("3Ds View Page: error has occurred");
                    if (webViewListener != null) {
                        webViewListener.onResult();
                    }

                    return;
                }
            }
        });
    }

    public YoucanPayWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void loadResult(Result result) {
        this.listenerUrl = result.listenUrl;
        this.loadDataWithBaseURL("", result.threeDsPage, "text/html", "utf-8", null);
    }

}
