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
import com.youcanPay.models.YCPayResponse;
import com.youcanPay.utils.Strings;
import com.youcanPay.youcanpay.R;

import static com.youcanPay.config.YCPayConfig.locale;

public class YCPayBottomSheet implements DialogInterface.OnDismissListener, YCPayWebViewCallbackImpl {
    private boolean isWebViewFinish = false;
    private final Dialog bottomDialog;
    private final YCPayResponse ycPayResult;
    private final PayCallbackImpl payCallback;

    public YCPayBottomSheet
            (
                    @NonNull Context context,
                    @NonNull YCPayResponse ycPayResult,
                    @NonNull PayCallbackImpl payCallback
            ) {
        this.ycPayResult = ycPayResult;
        this.payCallback = payCallback;
        this.bottomDialog = initBottomDialogView(context);
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
    private Dialog initBottomDialogView(Context context) {
        final Dialog bottomDialog = new Dialog(context, R.style.BottomDialogs);
        View view = LayoutInflater.from(context).inflate(R.layout.library_bottom_dialog, null);

        ImageView vIcon = view.findViewById(R.id.close_btn);
        YCPayWebView webView = view.findViewById(R.id.webView);

        vIcon.setOnClickListener(v -> dismiss());

        webView.setOnFinishCallback(this);
        webView.loadResult(this.ycPayResult);
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
            this.payCallback.onFailure(Strings.get("payment_canceled", locale));
        }
    }

    @Override
    public void onFinish() {
        this.isWebViewFinish = true;
        dismiss();
    }
}