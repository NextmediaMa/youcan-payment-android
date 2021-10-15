package com.youcan.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.youcan.payment.interfaces.YCPayWebViewCallBackImpl;
import com.youcan.payment.interfaces.YCPaymentCallBackImpl;
import com.youcan.payment.models.YCPaymentCallBakParams;
import com.youcan.youcanpaymentandroid.R;
import com.youcan.payment.interfaces.PayCallBackImpl;
import com.youcan.payment.models.YCPayCardInformation;
import com.youcan.payment.models.YCPayResult;
import com.youcan.payment.view.YCPayWebView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private YCPayWebView ycPayWebView;
    Button button;
    ProgressDialog progressBar;

    URL balanceCallBackUrl;
    String sellerToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvd3d3LmNhbnNoaXB5LmNvbVwvYXBpXC9zZWxsZXJzXC9hdXRoXC9sb2dpbiIsImlhdCI6MTYzMjQxNzAzMSwiZXhwIjoxNjM1MDA5MDMxLCJuYmYiOjE2MzI0MTcwMzEsImp0aSI6InlIYkRoS295enFWWGpNakkiLCJzdWIiOiIzYjMyNzIxMS0yYjllLTRlOGEtYjE1MC0zMjc1OGY0OWNkYmUiLCJwcnYiOiJjMDk5ODBjZTRkYjM1YzQ4NjM2YjFkYzZiYzQwZGZiMGRkMWFlNWU0IiwidXNlciI6eyJpZCI6IjNiMzI3MjExLTJiOWUtNGU4YS1iMTUwLTMyNzU4ZjQ5Y2RiZSIsImZ1bGxfbmFtZSI6ImFiZGVsbWppZCBzZWxsZXIiLCJwaG9uZSI6IjIxMjYxMDU3MzM0MiJ9fQ.DiaWcfwF6phfszManEo9ZOvMUEIDzrP0kkZs2kVNx60";

    HashMap<String, String> header = new HashMap<String, String>() {{
        put("Accept", "application/json");
        put("Authorization", "Bearer " + sellerToken);
    }};

    YCPaymentCallBakParams ycPaymentCallBakParams;
    PayCallBackImpl onPayListener;

    YCPayCardInformation cardInformation;
    String TAG = "YCPay";
    YCPay ycPay = new YCPay("pub_40526e71-75b8-4258-a898-b0a44c53", "a91ae2de-6942-486e-93c6-93ab27275dae");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();

        try {
            initYCPaymentHandler();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        initYCPay();
        // set on finish webView listener
        ycPayWebView.setWebViewListener(new YCPayWebViewCallBackImpl() {

            @Override
            public void onPaySuccess() {
                Toast.makeText(MainActivity.this, "PaySuccess 3D", Toast.LENGTH_LONG).show();
                Log.e(TAG, "onPaySuccess: ");
            }

            @Override
            public void onPayFailure(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                Log.e(TAG, "onPayFailure: " + message);
            }
        });

        // set on button clicked action
        button.setOnClickListener(v -> {
            ycPay.pay(cardInformation, onPayListener);
            progressBar.show();
        });
    }

    /**
     * inti view component
     */
    private void initComponent() {
        button = findViewById(R.id.button);
        ycPayWebView = findViewById(R.id.webPaiment);
        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Waiting");
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setCancelable(false);
    }

    private void initYCPaymentHandler() throws MalformedURLException {
        // create BalanceCallBak Params
        balanceCallBackUrl = new URL("https://www.canshipy.com/api/sellers/payment/callback");
        ycPaymentCallBakParams = new YCPaymentCallBakParams(header);
        ycPay.ycPaymentCallBack.create(balanceCallBackUrl, ycPaymentCallBakParams);
    }

    private void initYCPay() {
        // create PayCallBackImpl listener
        onPayListener = new PayCallBackImpl() {
            @Override
            public void onPaySuccess(YCPayResult response) {
                Log.e(TAG, response.toString());
                Toast.makeText(MainActivity.this, "PaySuccess", Toast.LENGTH_LONG).show();
                dismissProgressDialog();
            }

            @Override
            public void onPayFailure(String response) {
                Log.e(TAG, "onPayFailure: " + response);
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                dismissProgressDialog();
            }

            @Override
            public void on3DsResult(YCPayResult result) {
                Log.e(TAG, "Load3DSPage: " + result.listenUrl);
                ycPayWebView.loadResultUrl(result);
                dismissProgressDialog();
            }
        };

        cardInformation = new YCPayCardInformation("Abdelmjid ASOUAB", "4552949183673104", "02/22", "375"); //message=System+malfunction
    }

    private void paymentCallBackHandler() throws Exception {

        ycPay.ycPaymentCallBack.call(ycPay.token.getTransactionId(), new YCPaymentCallBackImpl() {
            @Override
            public void onSuccess(int statusResult) {

            }

            @Override
            public void onError(String message) {

            }
        });
    }

    void dismissProgressDialog() {
        if (progressBar.isShowing())
            progressBar.dismiss();
    }
}