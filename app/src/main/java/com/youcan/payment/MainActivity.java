package com.youcan.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.youcan.payment.instrafaces.YCPaymentCallBackImpl;
import com.youcan.payment.instrafaces.YCPayTokenizerCallBackImpl;
import com.youcan.payment.models.YCPaymentCallBakParams;
import com.youcan.payment.models.YCPayToken;
import com.youcan.youcanpaymentandroid.R;
import com.youcan.payment.instrafaces.PayCallBackImpl;
import com.youcan.payment.models.CardInformation;
import com.youcan.payment.models.YCPayResult;
import com.youcan.payment.models.YCPayTokenizerParams;
import com.youcan.payment.view.YCPayWebView;

import java.util.HashMap;

import static com.youcan.payment.config.YCPayStatus.canceled;
import static com.youcan.payment.config.YCPayStatus.pending;
import static com.youcan.payment.config.YCPayStatus.success;

public class MainActivity extends AppCompatActivity {
    private YCPayWebView webPaiment;
    Button button;
    CardInformation cardInformation = new CardInformation("abdelmjid", "4111111111111111", "10/24", "000");
    String tokenizerUrl = "https://www.canshipy.com/api/sellers/payment";
    String balanceCallBackUrl = "https://www.canshipy.com/api/sellers/payment/callback";
    String sellerToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvd3d3LmNhbnNoaXB5LmNvbVwvYXBpXC9zZWxsZXJzXC9hdXRoXC9sb2dpbiIsImlhdCI6MTYzMjQxNzAzMSwiZXhwIjoxNjM1MDA5MDMxLCJuYmYiOjE2MzI0MTcwMzEsImp0aSI6InlIYkRoS295enFWWGpNakkiLCJzdWIiOiIzYjMyNzIxMS0yYjllLTRlOGEtYjE1MC0zMjc1OGY0OWNkYmUiLCJwcnYiOiJjMDk5ODBjZTRkYjM1YzQ4NjM2YjFkYzZiYzQwZGZiMGRkMWFlNWU0IiwidXNlciI6eyJpZCI6IjNiMzI3MjExLTJiOWUtNGU4YS1iMTUwLTMyNzU4ZjQ5Y2RiZSIsImZ1bGxfbmFtZSI6ImFiZGVsbWppZCBzZWxsZXIiLCJwaG9uZSI6IjIxMjYxMDU3MzM0MiJ9fQ.DiaWcfwF6phfszManEo9ZOvMUEIDzrP0kkZs2kVNx60";
    HashMap<String, String> header = new HashMap<String, String>() {{
        put("Accept", "application/json");
        put("Authorization", "Bearer " + sellerToken);
    }};
    YCPaymentCallBakParams ycPaymentCallBakParams;
    String TAG = "YCPay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();
        callYCPayTokenizer();
        initYCPay();

        // set on finish webView listener
        webPaiment.setWebViewListener(() -> Toast.makeText(getApplicationContext(), "Web View", Toast.LENGTH_LONG).show());

        // set on button clicked action
        button.setOnClickListener(v -> YCPay.pay.call());


    }

    /**
     * inti view component
     */
    private void initComponent() {
        button = findViewById(R.id.button);
        webPaiment = findViewById(R.id.webPaiment);
    }

    private void callYCPayTokenizer() {
        // your Tokenizer request header
        HashMap<String, String> header = new HashMap<String, String>() {{
            put("Accept", "application/json");
            put("Authorization", "Bearer " + sellerToken);
        }};

        // create Tokenizer Params
        YCPayTokenizerParams ycPayTokenizerParams = new YCPayTokenizerParams(200f, header);

        // create Tokenizer listener Callback
        YCPayTokenizerCallBackImpl tokenizerListener = new YCPayTokenizerCallBackImpl() {
            @Override
            public void onSuccess(YCPayToken token) {
                initYCPaymentHandler();
            }

            @Override
            public void onError(String response) {
                Log.e(TAG, "onError: " + response);
                //...
            }
        };

        // generate new YCPay token
        YCPay.ycPayTokenizer.create(tokenizerUrl, ycPayTokenizerParams, tokenizerListener);
    }

    private void initYCPaymentHandler() {
        // create BalanceCallBak Params
        ycPaymentCallBakParams = new YCPaymentCallBakParams(header);
        YCPay.ycPaymentCallBack.create(balanceCallBackUrl, ycPaymentCallBakParams);
    }

    private void initYCPay() {

        // create PayCallBackImpl listener
        PayCallBackImpl onPayListener = new PayCallBackImpl() {
            @Override
            public void onPaySuccess(YCPayResult response) {
                Log.e(TAG, response.toString());
            }

            @Override
            public void onPayFailure(String response) {
                Log.e(TAG, "onPayFailure: " + response);
                paymentCallBackHandler();
            }

            @Override
            public void on3DsResult(YCPayResult result) {
                Log.e(TAG, "Load3DSPage: " + result.listenUrl);
                webPaiment.loadResult(result);
            }

        };

        // card Information
        CardInformation cardInformation = new CardInformation("Abde", "4111111111111111", "10/24", "000");

        // set Card information and YCPayListener
        YCPay.pay
                .setCardInformation(cardInformation)
                .setListener(onPayListener);
    }

    private void paymentCallBackHandler() {
        YCPay.ycPaymentCallBack.call(YCPay.pay.getToken().getTransactionId(), new YCPaymentCallBackImpl() {
            @Override
            public void onSuccess(int statusResult) {
                switch (statusResult) {
                    case canceled:
                        Log.i(TAG, "Payment Canceled");
                        break;

                    case pending:
                        Log.i(TAG, "Payment Pending ");
                        break;

                    case success:
                        Log.i(TAG, "Payment Success");
                        break;
                }
            }

            @Override
            public void onError(String error) {
                Log.i(TAG, "onError: "+error);
            }
        });
    }
}