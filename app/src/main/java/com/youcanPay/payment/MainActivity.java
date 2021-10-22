package com.youcanPay.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.youcanPay.YCPay;
import com.youcanPay.interfaces.YCPayWebViewCallBackImpl;
import com.youcanPay.interfaces.YCPaymentCallBackImpl;
import com.youcanPay.models.YCPaymentCallBakParams;
import com.youcanPay.youcanpaymentandroid.R;
import com.youcanPay.interfaces.PayCallBackImpl;
import com.youcanPay.models.YCPayCardInformation;
import com.youcanPay.models.YCPayResult;
import com.youcanPay.view.YCPayWebView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static com.youcanPay.config.YCPayConfig.YCP_TAG;

public class MainActivity extends AppCompatActivity {

    private YCPayWebView ycPayWebView;
    private Button button;
    private ProgressDialog progressBar;

    private PayCallBackImpl onPayListener;

    private YCPayCardInformation cardInformation;
    private YCPay ycPay = new YCPay("pub-key", "token-id");

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

        // init YCPay
        initYCPay();

        // set on finish webView listener
        initWebViewListener();

        // set on button clicked action
        button.setOnClickListener(v -> onPayPressed());
    }

    private void initWebViewListener() {
        ycPayWebView.setWebViewListener(new YCPayWebViewCallBackImpl() {
            @Override
            public void onPaySuccess() {
                Toast.makeText(MainActivity.this, "PaySuccess 3D", Toast.LENGTH_LONG).show();
                Log.e(YCP_TAG, "onPaySuccess: ");
            }

            @Override
            public void onPayFailure(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                Log.e(YCP_TAG, "onPayFailure: " + message);
            }
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
        String sellerToken = "token";
        HashMap<String, String> header = new HashMap<String, String>() {{
            put("Accept", "application/json");
            put("Authorization", "Bearer " + sellerToken);
        }};
        URL balanceCallBackUrl = new URL("https://preprod.allomystar.com/api/payment/callback/");
        YCPaymentCallBakParams ycPaymentCallBakParams = new YCPaymentCallBakParams(header);

        ycPay.ycPaymentCallBack.create(balanceCallBackUrl, ycPaymentCallBakParams);
    }

    private void initYCPay() {
        // create PayCallBackImpl listener
        onPayListener = new PayCallBackImpl() {
            @Override
            public void onPaySuccess(YCPayResult response) {
                Toast.makeText(MainActivity.this, "PaySuccess", Toast.LENGTH_LONG).show();
                dismissProgressDialog();
            }

            @Override
            public void onPayFailure(String response) {
                Log.e(YCP_TAG, "onPayFailure: " + response);
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                dismissProgressDialog();
            }

            @Override
            public void on3DsResult(YCPayResult result) {
                Log.e(YCP_TAG, "Load3DSPage: " + result.listenUrl);
                ycPayWebView.loadResultUrl(result);
                dismissProgressDialog();
            }
        };

        cardInformation = new YCPayCardInformation("name-card-holder", "4242424242424242", "10/24", "115"); //message=System+malfunction
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

    private void onPayPressed() {
        try {
            ycPay.pay(cardInformation, onPayListener);
            progressBar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void dismissProgressDialog() {
        if (progressBar.isShowing())
            progressBar.dismiss();
    }
}