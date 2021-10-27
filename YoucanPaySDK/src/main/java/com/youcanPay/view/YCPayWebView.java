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
import com.youcanPay.models.YCPayResult;
import com.youcanPay.youcanpay.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.regex.Pattern;

import static com.youcanPay.config.YCPayConfig.UNEXPECTED_ERROR;
import static com.youcanPay.config.YCPayConfig.YCP_TAG;

public class YCPayWebView extends WebView {
    private String returnUrl = "";
    private PayCallbackImpl webViewListener;
    private YCPayWebViewCallbackImpl onFinishCallback;
    private InputStream threeDSPage;
    private Context context;

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
        this.context = context;

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
                    webViewListener.onFailure("3Ds: " + UNEXPECTED_ERROR);
                    onFinishCallback.onFinish();
                }
            }
        });
    }

    public YCPayWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void loadResult(YCPayResult result, boolean isSandboxMode) {
        this.returnUrl = result.returnUrl;
        Log.e(YCP_TAG, "isSandboxMode: webView " + isSandboxMode);

        if (isSandboxMode) {
            this.loadUrl(result.redirectUrl);

            return;
        }

        loadFileHTML(this.context);
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

    private void loadFileHTML(Context context) {
        this.threeDSPage = context.getResources().openRawResource(R.raw.sandbox_3ds_page);
        String htmText = "";
        byte[] buffer = new byte[0];
        try {
            buffer = new byte[threeDSPage.available()];
            threeDSPage.read(buffer);
            threeDSPage.close();
            htmText = new String(buffer);
            htmText = htmText.replace("u@%", this.returnUrl);

            Log.e(YCP_TAG, "initBottomDialog: " + htmText);
            this.loadDataWithBaseURL("", htmText, "text/html", "utf-8", null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
