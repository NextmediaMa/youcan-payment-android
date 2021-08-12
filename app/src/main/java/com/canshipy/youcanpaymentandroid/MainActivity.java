package com.canshipy.youcanpaymentandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.canshipy.youcanpaymentandroidsdk.YoucanPayment;
import com.canshipy.youcanpaymentandroidsdk.instrafaces.PayCallBack;
import com.canshipy.youcanpaymentandroidsdk.models.CardInformation;
import com.canshipy.youcanpaymentandroidsdk.models.Result;
import com.canshipy.youcanpaymentandroidsdk.task.TestLoadHtml;

public class MainActivity extends AppCompatActivity {
    private WebView webPaiment;
    Button button ;
    YoucanPayment youcanPayment = new YoucanPayment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        webPaiment = findViewById(R.id.webPaiment);
        webPaiment.getSettings().setJavaScriptEnabled(true);
        webPaiment.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        youcanPayment.initilaze.setAmount(11.9).call();
     /*   button.setOnClickListener(
                v->
                 youcanPayment.pay
                .setCardInformation(new CardInformation("abdelmjid","4111111111111111","10/24","000"))
                .call(new PayCallBack() {
                    @Override
                    public void onPaySuccess(Result response) {
                        Log.e("build_test", response.toString() );
                    }

                    @Override
                    public void onPayFailure(String response) {
                        Log.e("build_test", response );
                    }
                }));*/




        button.setOnClickListener(a-> YoucanPayment.loadHtml(new PayCallBack() {
            @Override
            public void onPaySuccess(Result result) {

            }

            @Override
            public void onPayFailure(String response) {
                Log.e("build_test", response );
                runOnUiThread(() -> startWebView(response));


               //startWebView(response);
            }
        }));
    }

    private void startWebView(String contnt) {

        webPaiment.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Log.e("build_test_url_1", url );
                return true;
            }

            //Show loader on url load
            @Override
            public void onLoadResource(WebView view, String url) {
                Log.e("build_test_url_4", url );

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.e("build_test_url_3", url );
            }

            @Override
            public void onPageFinished(WebView view, String url) {
               // swipeRefreshLayout.setRefreshing(false);
                Log.e("build_test_url_2", url );

                try {
                    if (url.contains("/fpay/callback/mobile")) {
                        //new GetPaimentData().execute(url);
                    }

                    if (url.contains("/paypal/callback/mobile")) {
                        //new GetPaimentData().execute(url);
                    }

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        //Load html in webView
        webPaiment.loadDataWithBaseURL("",contnt,"text/html","utf-8",null);
    }
}