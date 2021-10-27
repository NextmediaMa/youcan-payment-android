package com.youcanPay.payment.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.youcanPay.YCPay
import com.youcanPay.config.YCPayConfig
import com.youcanPay.interfaces.PayCallbackImpl
import com.youcanPay.models.YCPayCardInformation
import com.youcanPay.models.YCPayResult
import com.youcanPay.payment.Utils
import com.youcanPay.youcanpaymentandroid.R

class MainActivity2 : AppCompatActivity(), PayCallbackImpl {

    private var button: Button? = null
    private var progressBar: ProgressDialog? = null
    private var nameHolderInput: TextInputEditText? = null
    private var cardNumInput: TextInputEditText? = null
    private var expiryDateInput: TextInputEditText? = null
    private var cvvInput: TextInputEditText? = null

    private var cardInformation: YCPayCardInformation? = null
    private var ycPay: YCPay? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponent()
        initTextWatcher()
        initYCPay()

        // set on button clicked action
        button!!.setOnClickListener { v: View? -> onPayPressed() }

    }

    /**
     * inti view component
     */
    private fun initComponent() {
        button = findViewById(R.id.btnPay)
        nameHolderInput = findViewById(R.id.holder_name)
        cardNumInput = findViewById(R.id.card_number)
        expiryDateInput = findViewById(R.id.expiry_date)
        cvvInput = findViewById(R.id.code_cvv)
        progressBar = ProgressDialog(this)
        progressBar!!.setCanceledOnTouchOutside(false)
        progressBar!!.setCancelable(false)
    }


    private fun initTextWatcher() {
        val expiryTextWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.length > 0 && editable.length % 3 == 0) {
                    val c = editable[editable.length - 1]
                    if ('/' == c) {
                        editable.delete(editable.length - 1, editable.length)
                    }
                }
                if (editable.length > 0 && editable.length % 3 == 0) {
                    val c = editable[editable.length - 1]
                    if (Character.isDigit(c) && TextUtils.split(editable.toString(), "/").size <= 2) {
                        editable.insert(editable.length - 1, "/")
                    }
                }
            }
        }
        expiryDateInput!!.addTextChangedListener(expiryTextWatcher)
    }

    private fun initYCPay() {
        // Here u have to call your initializer to get your token id
        ycPay = YCPay(this, "pub_40526e71-75b8-4258-a898-b0a44c53")
    }

    private fun onPayPressed() {
        try {
            getCardInfo()
            ycPay?.pay("f2492b88-bf4c-45bc-ac06-6fb446491bf2", cardInformation, this)
            showProgressDialog("waiting")
            button!!.isFocusable = true
            Utils.hideKeyboard(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun getCardInfo() {
        val name = nameHolderInput!!.text.toString()
        val cardNum = cardNumInput!!.text.toString()
        val date = expiryDateInput!!.text.toString()
        val cvv = cvvInput!!.text.toString()
        if (name == "" || cardNum == "" || date == "" || cvv == "") {
            Toast.makeText(this, "please set card information", Toast.LENGTH_LONG).show()
            return
        }
        cardInformation = YCPayCardInformation(name, cardNum, date.split("/".toRegex()).toTypedArray()[0], date.split("/".toRegex()).toTypedArray()[1], cvv)
    }

    fun showProgressDialog(message: String?) {
        progressBar!!.setMessage(message)
        progressBar!!.show()
    }

    fun dismissProgressDialog() {
        if (progressBar!!.isShowing) progressBar!!.dismiss()
    }

    override fun onSuccess(result: String?) {
        Toast.makeText(this, "PaySuccess", Toast.LENGTH_LONG).show()
        dismissProgressDialog()
    }

    override fun onFailure(message: String?) {
        Log.e(YCPayConfig.YCP_TAG, "onPayFailure: $message")
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        dismissProgressDialog()
    }

}