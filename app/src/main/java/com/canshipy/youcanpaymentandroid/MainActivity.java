package com.canshipy.youcanpaymentandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.canshipy.youcanpaymentandroidsdk.YoucanPayment;
import com.canshipy.youcanpaymentandroidsdk.instrafaces.PayCallBack;
import com.canshipy.youcanpaymentandroidsdk.models.CardInformation;
import com.canshipy.youcanpaymentandroidsdk.models.Result;
import com.canshipy.youcanpaymentandroidsdk.view.YoucanPayWebView;

public class MainActivity extends AppCompatActivity {
    private YoucanPayWebView webPaiment;
    Button button ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        webPaiment =  findViewById(R.id.webPaiment);

        YoucanPayment.initilaze.setAmount(11.9).call();

        button.setOnClickListener(
                v->
                 YoucanPayment.pay
                .setCardInformation(new CardInformation("abdelmjid","4111111111111111","10/24","000"))
                .setListener(new PayCallBack() {
                    @Override
                    public void onPaySuccess(Result response) {
                        Log.e("build_test", response.toString() );
                        Toast.makeText(getApplicationContext(), "Pay Success", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onPayFailure(String response) {
                        Log.e("build_test", response );
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void on3DsResult(Result result) {
                        Log.e("callbackUrl", "Load3DSPage: "+result.listenUrl );
                        webPaiment.loadResult(result);
                    }
                })
                .call());

    }

}