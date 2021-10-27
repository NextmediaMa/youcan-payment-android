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
import com.youcanPay.youcanpay.R;

public class YCPayBottomDialog implements DialogInterface.OnDismissListener, YCPayWebViewCallbackImpl {
    protected final Builder mBuilder;
    protected ImageView vIcon;
    protected YCPayWebView webView;
    private boolean isWebViewFinish = false;

    public final Builder getBuilder() {
        return mBuilder;
    }

    public final ImageView getIconImageView() {
        return vIcon;
    }

    protected YCPayBottomDialog(Builder builder) {
        mBuilder = builder;
        mBuilder.bottomDialog = initBottomDialog(builder);
        mBuilder.bottomDialog.setOnDismissListener(this);
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

        this.vIcon = view.findViewById(R.id.close_btn);
        this.webView = view.findViewById(R.id.webView);

        this.webView.setOnFinishCallback(this);

        this.vIcon.setOnClickListener(v -> {
            mBuilder.payCallback.onFailure("payment canceled");

            dismiss();
        });

        if (builder.ycPayResult != null) {
            this.webView.loadResult(builder.ycPayResult, builder.isSandboxMode);
        }

        if (builder.payCallback != null) {
            this.webView.setWebViewListener(builder.payCallback);
        }

        bottomDialog.setContentView(view);
        bottomDialog.setCancelable(true);
        bottomDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);

        return bottomDialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (!isWebViewFinish && mBuilder.payCallback != null)
            mBuilder.payCallback.onFailure("payment canceled");
    }

    @Override
    public void onFinish() {
        this.isWebViewFinish = true;
        dismiss();

    }

    public static class Builder {
        protected Context context;
        // Bottom Dialog
        protected Dialog bottomDialog;
        protected YCPayResult ycPayResult;
        protected PayCallbackImpl payCallback;
        protected boolean isSandboxMode;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Builder setData(@NonNull YCPayResult ycPayResult, @NonNull PayCallbackImpl payCallback, boolean isSandboxMode) {
            this.ycPayResult = ycPayResult;
            this.payCallback = payCallback;
            this.isSandboxMode = isSandboxMode;
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