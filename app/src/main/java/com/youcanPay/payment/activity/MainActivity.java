package com.youcanPay.payment.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.youcanPay.YCPay;
import com.youcanPay.exception.YCPayInvalidArgumentException;
import com.youcanPay.exception.YCPayInvalidPaymentMethodException;
import com.youcanPay.interfaces.CashPlusCallbackImpl;
import com.youcanPay.interfaces.PayCallbackImpl;
import com.youcanPay.models.YCPayCardInformation;
import com.youcanPay.payment.TextWatchers.CardNumberTextWatcher;
import com.youcanPay.payment.TextWatchers.DateExpireTextWatcher;
import com.youcanPay.youcanpaymentandroid.R;

import static com.youcanPay.payment.Utils.hideKeyboard;

public class MainActivity extends AppCompatActivity implements PayCallbackImpl, CashPlusCallbackImpl {
    private Button button;
    private ProgressDialog progressBar;
    private TextInputEditText nameHolderInput, cardNumInput, expiryDateInput, cvvInput;
    private YCPayCardInformation cardInformation;
    private YCPay ycPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
        initTextWatcher();
        initYCPay();

        // set on button clicked action
        this.button.setOnClickListener(v -> {
            try {
                onPayPressed();
            } catch (YCPayInvalidArgumentException | YCPayInvalidPaymentMethodException e) {
                e.printStackTrace();
                this.onFailure(e.getMessage());
            }
        });
    }

    /**
     * inti view component
     */
    private void initComponent() {
        this.button = findViewById(R.id.btnPay);
        this.nameHolderInput = findViewById(R.id.holder_name);
        this.cardNumInput = findViewById(R.id.card_number);
        this.expiryDateInput = findViewById(R.id.expiry_date);
        this.cvvInput = findViewById(R.id.code_cvv);
        this.progressBar = new ProgressDialog(this);
        this.progressBar.setCanceledOnTouchOutside(false);
        this.progressBar.setCancelable(false);
    }

    private void initTextWatcher() {
        expiryDateInput.addTextChangedListener(new DateExpireTextWatcher());
        cardNumInput.addTextChangedListener(new CardNumberTextWatcher());
    }

    private void initYCPay() {
        // Here u have to call your initializer to get your token id
        this.ycPay = new YCPay(
                this,
                "pub_782a5a9d-1379-4e64-9a3f-5c8d5bb2",
                "fr"
        );
    }

    private void onPayPressed() throws YCPayInvalidArgumentException, YCPayInvalidPaymentMethodException {
        this.cardInformation = new YCPayCardInformation(
                "abdelmjid",
                "4242424242424242",
                "10",
                "24",
                "112"
        );

        this.ycPay.payWithCashPlus(
                "5b77057f-7556-4590-8d10-f324d08b7f0a",
                // this.cardInformation,
                this
        );

        showProgressDialog("waiting");
        hideKeyboard(this);
    }

    private void getCardInfo() throws YCPayInvalidArgumentException {
        String name = this.nameHolderInput.getText().toString();
        String cardNum = this.cardNumInput.getText().toString();
        String date = this.expiryDateInput.getText().toString();
        String cvv = this.cvvInput.getText().toString();

        this.cardInformation = new YCPayCardInformation(
                name,
                cardNum,
                date.split("/")[0],
                date.split("/")[1],
                cvv
        );
    }

    public void showProgressDialog(String message) {
        this.progressBar.setMessage(message);
        this.progressBar.show();
    }

    public void dismissProgressDialog() {
        if (this.progressBar.isShowing()) {
            this.progressBar.dismiss();
        }
    }

    @Override
    public void onSuccess(String transactionId) {
        Toast.makeText(
                MainActivity.this,
                "Pay Success: " + transactionId,
                Toast.LENGTH_SHORT
        ).show();
        dismissProgressDialog();
    }

    @Override
    public void onSuccess(String transactionId, String token) {
        Toast.makeText(
                MainActivity.this,
                "Token : " + token,
                Toast.LENGTH_SHORT
        ).show();
        dismissProgressDialog();
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(
                MainActivity.this,
                message,
                Toast.LENGTH_LONG
        ).show();
        dismissProgressDialog();
    }
}