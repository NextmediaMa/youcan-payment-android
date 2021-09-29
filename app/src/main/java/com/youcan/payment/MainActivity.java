package com.youcan.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.youcan.payment.instrafaces.YCPayBalanceCallBackImpl;
import com.youcan.payment.models.YCPayBalanceCallBakParams;
import com.youcan.payment.models.YCPayBalanceResult;
import com.youcan.youcanpaymentandroid.R;
import com.youcan.payment.instrafaces.PayCallBackImpl;
import com.youcan.payment.models.CardInformation;
import com.youcan.payment.models.YCPayResult;
import com.youcan.payment.models.YCPayTokenizerParams;
import com.youcan.payment.view.YCPayWebView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private YCPayWebView webPaiment;
    Button button;
    CardInformation cardInformation = new CardInformation("abdelmjid", "4111111111111111", "10/24", "000");
    String tokenizerUrl = "https://www.canshipy.com/api/sellers/payment";
    String balanceCallBack = "https://www.canshipy.com/api/sellers/payment/callback";
    String sellerToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvd3d3LmNhbnNoaXB5LmNvbVwvYXBpXC9zZWxsZXJzXC9hdXRoXC9sb2dpbiIsImlhdCI6MTYzMjQxNzAzMSwiZXhwIjoxNjM1MDA5MDMxLCJuYmYiOjE2MzI0MTcwMzEsImp0aSI6InlIYkRoS295enFWWGpNakkiLCJzdWIiOiIzYjMyNzIxMS0yYjllLTRlOGEtYjE1MC0zMjc1OGY0OWNkYmUiLCJwcnYiOiJjMDk5ODBjZTRkYjM1YzQ4NjM2YjFkYzZiYzQwZGZiMGRkMWFlNWU0IiwidXNlciI6eyJpZCI6IjNiMzI3MjExLTJiOWUtNGU4YS1iMTUwLTMyNzU4ZjQ5Y2RiZSIsImZ1bGxfbmFtZSI6ImFiZGVsbWppZCBzZWxsZXIiLCJwaG9uZSI6IjIxMjYxMDU3MzM0MiJ9fQ.DiaWcfwF6phfszManEo9ZOvMUEIDzrP0kkZs2kVNx60";
    HashMap<String, String> header = new HashMap<String, String>() {{
        put("Accept", "application/json");
        put("Authorization", "Bearer " + sellerToken);
    }};
    YCPayBalanceCallBakParams ycPayBalanceCallBakParams;
    String TAG = "YCPay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();

        // disable test mode
        YCPay.testMode(false);

        callYCPayTokenizer();
        initYCPayBalanceCallBack();
        initYCPay();

        // set on finish webView listener
        webPaiment.setWebViewListener(() -> Toast.makeText(getApplicationContext(), "Web View", Toast.LENGTH_LONG).show());

        // set on button clicked action
        button.setOnClickListener(
                v -> YCPay.pay
                        .call());
    }

    /**
     * inti view component
     */
    private void initComponent() {
        button = findViewById(R.id.button);
        webPaiment = findViewById(R.id.webPaiment);
    }

    private void callYCPayTokenizer() {
        // create Tokenizer Params
        YCPayTokenizerParams ycPayTokenizerParams = new YCPayTokenizerParams(tokenizerUrl, 200f, header);
        // create new Pay token
        YCPay.ycPayTokenizer.create(ycPayTokenizerParams);
    }

    private void initYCPayBalanceCallBack() {
        // create BalanceCallBak Params
        ycPayBalanceCallBakParams = new YCPayBalanceCallBakParams(balanceCallBack, header);
        // create BalanceCallBak listener
        YCPayBalanceCallBackImpl balanceCallBackListener = new YCPayBalanceCallBackImpl() {
            @Override
            public void onResponse(YCPayBalanceResult balanceResult) {
                Log.e(TAG, "onResponse: " + balanceResult.toString());
            }

            @Override
            public void onError(String response) {
                Log.e(TAG, "onResponse: " + response);
            }
        };
        // set YCPayCallBack listener
        YCPay.ycPayBalanceCallBack.setListener(balanceCallBackListener);
    }

    private void initYCPay() {
        // create PayCallBackImpl listener
        PayCallBackImpl payListener = new PayCallBackImpl() {
            @Override
            public void onPaySuccess(YCPayResult response) {
                Log.e(TAG, response.toString());
                Toast.makeText(getApplicationContext(), "Pay Success", Toast.LENGTH_LONG).show();
                YCPay.ycPayBalanceCallBack.create(ycPayBalanceCallBakParams);
            }

            @Override
            public void onPayFailure(String response) {
                Log.e(TAG, "onPayFailure: " + response);
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
            }

            @Override
            public void on3DsResult(YCPayResult result) {
                Log.e(TAG, "Load3DSPage: " + result.listenUrl);
                webPaiment.loadResult(result);
            }
        };
        // set Card information and YCPayListener
        YCPay.pay
                .setCardInformation(cardInformation)
                .setListener(payListener);
    }
}