package com.youcan.payment.task;

import android.os.AsyncTask;
import android.util.Log;

import com.youcan.payment.instrafaces.YCPayBalanceCallBackImpl;
import com.youcan.payment.models.YCPayBalanceCallBakParams;
import com.youcan.payment.models.YCPayBalanceResult;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class YCPayBalanceCallBackTask extends AsyncTask<String, Void, YCPayBalanceResult> {

    YCPayBalanceCallBakParams params;
    Request.Builder requestBuilder;
    YCPayBalanceCallBackImpl onResultListener;
    Request request;

    public YCPayBalanceCallBackTask(YCPayBalanceCallBakParams params, RequestBody formBody, YCPayBalanceCallBackImpl listenerToken) {
        initRequest(params, formBody);
        this.onResultListener = listenerToken;
    }

    void initRequest(YCPayBalanceCallBakParams params, RequestBody formBody) {
        Iterator hmIterator = params.getHeader().entrySet().iterator();

        this.params = params;
        this.requestBuilder = new Request.Builder();
        this.requestBuilder.url(params.getBalanceCallBackUrl())
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

        OkHttpClient okHttpClient = new OkHttpClient();

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

        onResultListener.onResponse(result);
    }
}