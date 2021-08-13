package com.canshipy.youcanpaymentandroidsdk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.canshipy.youcanpaymentandroidsdk.YoucanPayment;
import com.canshipy.youcanpaymentandroidsdk.models.Result;

public class YoucanPayWebView extends WebView {

    private String listenerUrl;
    public YoucanPayWebView(@NonNull Context context) {
        super(context);
    }

    public YoucanPayWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        this.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e("url_onPageFinished", "onPageFinished: "+url );
                Log.e("url_onPageFinished", "listener : "+listenerUrl );

                    try {
                        if(checkPayIsSuccess(url))
                            return;

                        if (url.contains("is_success=0")) {
                            YoucanPayment.payListener.onPayFailure("3Ds not Success");

                            return;
                        }

                        YoucanPayment.payListener.onPayFailure("3Ds View Page: error has occurred");

                        return;

                    } catch (Exception exception) {
                        exception.printStackTrace();
                        YoucanPayment.payListener.onPayFailure("3Ds View Page: error has occurred");

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
        this.loadDataWithBaseURL("",result.threeDsPage,"text/html","utf-8",null);
    }

    private boolean checkPayIsSuccess(String url){
        if(!url.contains(listenerUrl))
            return  false;
        if (url.contains("is_success=1")) {
                YoucanPayment.payListener.onPaySuccess(new Result());
                return  true;
        }

        return  false;
    }
}
