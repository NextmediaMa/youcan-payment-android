package com.youcanPay.payment.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.youcanPay.YCPay;
import com.youcanPay.interfaces.PayCallbackImpl;
import com.youcanPay.models.YCPayCardInformation;
import com.youcanPay.youcanpaymentandroid.R;

import static com.youcanPay.payment.Utils.hideKeyboard;

public class MainActivity extends AppCompatActivity implements PayCallbackImpl {
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
        this.button.setOnClickListener(v -> onPayPressed());
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
        TextWatcher expiryTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0 && (editable.length() % 3) == 0) {
                    final char c = editable.charAt(editable.length() - 1);
                    if ('/' == c) {
                        editable.delete(editable.length() - 1, editable.length());
                    }
                }
                if (editable.length() > 0 && (editable.length() % 3) == 0) {
                    char c = editable.charAt(editable.length() - 1);
                    if (Character.isDigit(c) && TextUtils.split(editable.toString(), String.valueOf("/")).length <= 2) {
                        editable.insert(editable.length() - 1, String.valueOf("/"));
                    }
                }
            }
        };

        expiryDateInput.addTextChangedListener(expiryTextWatcher);
    }

    private void initYCPay() {
        // Here u have to call your initializer to get your token id
        this.ycPay = new YCPay(this, "pub_key");
    }

    private void onPayPressed() {
        try {
            getCardInfo();

            this.ycPay.pay("token_id", this.cardInformation, this);
            showProgressDialog("waiting");
            hideKeyboard(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCardInfo() {
        String name = this.nameHolderInput.getText().toString();
        String cardNum = this.cardNumInput.getText().toString();
        String date = this.expiryDateInput.getText().toString();
        String cvv = this.cvvInput.getText().toString();

        if (name.equals("") || cardNum.equals("") || date.length() < 5 || cvv.equals("")) {
            Toast.makeText(this, "Please set card information", Toast.LENGTH_LONG).show();

            return;
        }

        this.cardInformation = new YCPayCardInformation(name, cardNum, date.split("/")[0], date.split("/")[1], cvv);
    }

    public void showProgressDialog(String message) {
        this.progressBar.setMessage(message);
        this.progressBar.show();
    }

    public void dismissProgressDialog() {
        if (this.progressBar.isShowing())
            this.progressBar.dismiss();
    }

    @Override
    public void onSuccess(String transactionId) {
        Toast.makeText(MainActivity.this, "Pay Success: " + transactionId, Toast.LENGTH_SHORT).show();
        dismissProgressDialog();
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        dismissProgressDialog();
    }
}