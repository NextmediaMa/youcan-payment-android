package com.youcan.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.youcan.youcanpaymentandroid.R;
import com.youcan.payment.instrafaces.PayCallBack;
import com.youcan.payment.models.CardInformation;
import com.youcan.payment.models.YCPayResult;
import com.youcan.payment.models.YCPayTokenizerParams;
import com.youcan.payment.view.YCPayWebView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private YCPayWebView webPaiment;
    Button button;
    CardInformation cardInformation = new CardInformation("abdelmjid", "4111111111111111", "10/24", "000");
    String tokenierUrl = "https://www.canshipy.com/api/sellers/payment";
    String sellerToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvd3d3LmNhbnNoaXB5LmNvbVwvYXBpXC9zZWxsZXJzXC9hdXRoXC9sb2dpbiIsImlhdCI6MTYzMjQxNzAzMSwiZXhwIjoxNjM1MDA5MDMxLCJuYmYiOjE2MzI0MTcwMzEsImp0aSI6InlIYkRoS295enFWWGpNakkiLCJzdWIiOiIzYjMyNzIxMS0yYjllLTRlOGEtYjE1MC0zMjc1OGY0OWNkYmUiLCJwcnYiOiJjMDk5ODBjZTRkYjM1YzQ4NjM2YjFkYzZiYzQwZGZiMGRkMWFlNWU0IiwidXNlciI6eyJpZCI6IjNiMzI3MjExLTJiOWUtNGU4YS1iMTUwLTMyNzU4ZjQ5Y2RiZSIsImZ1bGxfbmFtZSI6ImFiZGVsbWppZCBzZWxsZXIiLCJwaG9uZSI6IjIxMjYxMDU3MzM0MiJ9fQ.DiaWcfwF6phfszManEo9ZOvMUEIDzrP0kkZs2kVNx60";
    HashMap<String, String> header = new HashMap<String, String>() {{
        put("Accept", "application/json");
        put("Authorization", "Bearer " + sellerToken);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        webPaiment = findViewById(R.id.webPaiment);
        webPaiment.setWebViewListener(() -> {
            Toast.makeText(getApplicationContext(), "Web Vieww", Toast.LENGTH_LONG).show();
        });

        YCPayTokenizerParams params = new YCPayTokenizerParams(tokenierUrl, 100.0, header);
        YCPay.tokenizer.create(params);

        button.setOnClickListener(
                v ->
                        YCPay.pay
                                .setCardInformation(new CardInformation("abdelmjid", "4111111111111111", "10/24", "000"))
                                .setListener(new PayCallBack() {
                                    @Override
                                    public void onPaySuccess(YCPayResult response) {
                                        Log.e("YCPay", response.toString());
                                        Toast.makeText(getApplicationContext(), "Pay Success", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onPayFailure(String response) {
                                        Log.e("YCPay", response);
                                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void on3DsResult(YCPayResult result) {
                                        Log.e("callbackUrl", "Load3DSPage: " + result.listenUrl);
                                        webPaiment.loadResult(result);
                                    }
                                })
                                .call());
    }
}