package com.canshipy.youcanpaymentandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.canshipy.youcanpaymentandroidsdk.YoucanPayment;
import com.canshipy.youcanpaymentandroidsdk.instrafaces.PayCallBack;
import com.canshipy.youcanpaymentandroidsdk.models.CardInformation;
import com.canshipy.youcanpaymentandroidsdk.models.Result;

public class MainActivity extends AppCompatActivity {

    Button button ;
    YoucanPayment youcanPayment = new YoucanPayment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        youcanPayment.initilaze.setAmount(11.9).call();

        button.setOnClickListener(v-> youcanPayment.pay
                .setCardInformation(new CardInformation("abdelmjid","4111111111111111","10/24","000"))
                .call(new PayCallBack() {
                    @Override
                    public void onPaySuccess(Result response) {
                        Log.e("build_test", response.toString() );
                    }

                    @Override
                    public void onPayField(String response) {
                        Log.e("build_test", response );
                    }
                }));
    }
}