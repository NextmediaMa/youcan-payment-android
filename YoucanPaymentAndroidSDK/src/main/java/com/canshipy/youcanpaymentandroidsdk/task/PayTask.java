package com.canshipy.youcanpaymentandroidsdk.task;

import android.os.AsyncTask;

import com.canshipy.youcanpaymentandroidsdk.instrafaces.PayCallBack;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PayTask extends AsyncTask<String, Void, String> {

    String url;
    RequestBody formBody;
    PayCallBack onResultListener;

    public PayTask(String url, RequestBody formBody, PayCallBack listener) {
        this.url = url;
        this.formBody = formBody;
        this.onResultListener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("Accept", "application/json")
                .url(url)
                .post(formBody)
                .build();
        Response response;
        JSONObject result;
        try {
            response = okHttpClient.newCall(request).execute();
            result = new JSONObject(response.body().string());
            onResultListener.onPaySuccess(result.toString());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}