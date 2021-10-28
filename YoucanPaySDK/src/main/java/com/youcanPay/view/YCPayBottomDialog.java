package com.youcanPay.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;

import com.youcanPay.interfaces.PayCallbackImpl;
import com.youcanPay.interfaces.YCPayWebViewCallbackImpl;
import com.youcanPay.models.YCPayResult;
import com.youcanPay.utils.Strings;
import com.youcanPay.youcanpay.R;

public class YCPayBottomDialog implements DialogInterface.OnDismissListener, YCPayWebViewCallbackImpl {
    private final Builder mBuilder;
    private boolean isWebViewFinish = false;

    protected YCPayBottomDialog(Builder builder) {
        mBuilder = builder;
        mBuilder.bottomDialog = initBottomDialog(builder);
        mBuilder.bottomDialog.setOnDismissListener(this);
    }

    public final Builder getBuilder() {
        return mBuilder;
    }

    @UiThread
    public void show() {
        if (mBuilder != null && mBuilder.bottomDialog != null)
            mBuilder.bottomDialog.show();
    }

    @UiThread
    public void dismiss() {
        if (mBuilder != null && mBuilder.bottomDialog != null) {
            mBuilder.bottomDialog.dismiss();
        }
    }

    @UiThread
    private Dialog initBottomDialog(final Builder builder) {

        final Dialog bottomDialog = new Dialog(builder.context, R.style.BottomDialogs);
        View view = LayoutInflater.from(builder.context).inflate(R.layout.library_bottom_dialog, null);

        ImageView vIcon = view.findViewById(R.id.close_btn);
        YCPayWebView webView = view.findViewById(R.id.webView);

        webView.setOnFinishCallback(this);

        vIcon.setOnClickListener(v -> {
            paymentCanceled();
            dismiss();
        });

        if (builder.ycPayResult != null) {
            webView.loadResult(builder.ycPayResult, builder.local);
        }

        if (builder.payCallback != null) {
            webView.setWebViewListener(builder.payCallback);
        }

        bottomDialog.setContentView(view);
        bottomDialog.setCancelable(true);
        bottomDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);

        return bottomDialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (!isWebViewFinish && mBuilder.payCallback != null) {
            paymentCanceled();
        }
    }

    @Override
    public void onFinish() {
        this.isWebViewFinish = true;
        dismiss();
    }

    void paymentCanceled() {
        mBuilder.payCallback.onFailure(Strings.get("payment_canceled", this.mBuilder.local));
    }

    public static class Builder {
        private Context context;
        private Dialog bottomDialog;
        private YCPayResult ycPayResult;
        private PayCallbackImpl payCallback;
        private String local;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Builder setData(@NonNull YCPayResult ycPayResult, @NonNull PayCallbackImpl payCallback, String local) {
            this.ycPayResult = ycPayResult;
            this.payCallback = payCallback;
            this.local = local;
            return this;
        }

        @UiThread
        public YCPayBottomDialog build() {
            return new YCPayBottomDialog(this);
        }

        @UiThread
        public YCPayBottomDialog show() {
            YCPayBottomDialog bottomDialog = build();
            bottomDialog.show();

            return bottomDialog;
        }
    }
}