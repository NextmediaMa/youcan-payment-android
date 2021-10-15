package com.youcan.payment.task;

import android.os.AsyncTask;
import android.util.Log;

import com.youcan.payment.interfaces.YCPaymentCallBackImpl;
import com.youcan.payment.models.YCPaymentCallBakParams;
import com.youcan.payment.models.YCPayBalanceResult;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class YCPaymentCallBackTask extends AsyncTask<String, Void, YCPayBalanceResult> {

    YCPaymentCallBakParams params;
    Request.Builder requestBuilder;
    YCPaymentCallBackImpl onResultListener;
    Request request;

    public YCPaymentCallBackTask(String balanceCallBackUrl, String transactionId, YCPaymentCallBakParams params, YCPaymentCallBackImpl listenerToken) {
        initRequest(balanceCallBackUrl, transactionId, params);
        this.onResultListener = listenerToken;
    }

    void initRequest(String balanceCallBackUrl, String transactionId, YCPaymentCallBakParams params) {
        Iterator hmIterator = params.getHeader().entrySet().iterator();

        this.params = params;

        RequestBody formBody = new FormBody.Builder()
                .add("transaction_id", transactionId)
                .build();

        this.requestBuilder = new Request.Builder();
        this.requestBuilder.url(balanceCallBackUrl)
                .post(formBody);

        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) hmIterator.next();
            String value = (String) mapElement.getValue();
            this.requestBuilder.addHeader((String) mapElement.getKey(), value);
        }

        this.request = requestBuilder
                .build();
    }

    @Override
    protected YCPayBalanceResult doInBackground(String... strings) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);

        OkHttpClient okHttpClient = builder.build();

        Response response;
        String result;

        try {
            YCPayBalanceResult balanceResult = new YCPayBalanceResult();
            response = okHttpClient.newCall(request).execute();
            result = response.body().string();
            if (response.isSuccessful()) {
                Log.e("Response", "Balance result : " + result);
                balanceResult = new YCPayBalanceResult().resultFromJson(result);

                return balanceResult;
            }
            JSONObject jsonResult = new JSONObject(result);
            balanceResult.setMessageError(jsonResult.getString("message"));

            return balanceResult;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(YCPayBalanceResult result) {
        super.onPostExecute(result);
        if (result == null) {
            onResultListener.onError("an error has occurred ");

            return;
        }

        if (!result.getMessageError().equals("")) {
            onResultListener.onError(result.getMessageError());

            return;
        }

        onResultListener.onSuccess(result.getStatus());
    }
}