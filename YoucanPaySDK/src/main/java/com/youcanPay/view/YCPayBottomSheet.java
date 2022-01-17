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
import com.youcanPay.models.YCPResponse3ds;
import com.youcanPay.utils.YCPayStrings;
import com.youcanPay.youcanpay.R;

import static com.youcanPay.utils.YCPayLocale.getLocale;

public class YCPayBottomSheet implements DialogInterface.OnDismissListener, YCPayWebViewCallbackImpl {
    private boolean isWebViewFinish = false;
    private final Dialog bottomDialog;
    private final PayCallbackImpl payCallback;

    public YCPayBottomSheet(@NonNull Context context, @NonNull YCPResponse3ds ycPayResult,
                            @NonNull PayCallbackImpl payCallback) {
        this.payCallback = payCallback;
        this.bottomDialog = initBottomDialogView(context, ycPayResult);
    }

    @UiThread
    public void show() {
        this.bottomDialog.show();
    }

    @UiThread
    public void dismiss() {
        this.bottomDialog.dismiss();
    }

    @UiThread
    private Dialog initBottomDialogView(Context context, YCPResponse3ds ycPayResult) {
        final Dialog bottomDialog = new Dialog(context, R.style.BottomDialogs);

        final View view = LayoutInflater.from(context).inflate(R.layout.library_bottom_dialog,
                null);
        final ImageView vIcon = view.findViewById(R.id.close_btn);
        final YCPayWebView webView = view.findViewById(R.id.webView);

        vIcon.setOnClickListener(v -> dismiss());

        webView.setOnFinishCallback(this);
        webView.loadResult(ycPayResult);
        webView.setWebViewListener(this.payCallback);

        bottomDialog.setContentView(view);
        bottomDialog.setOnDismissListener(this);
        bottomDialog.setCancelable(true);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        return bottomDialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (!isWebViewFinish) {
            this.payCallback.onFailure(YCPayStrings.get("payment_canceled", getLocale()));
        }
    }

    @Override
    public void onFinish() {
        this.isWebViewFinish = true;
        dismiss();
    }
}