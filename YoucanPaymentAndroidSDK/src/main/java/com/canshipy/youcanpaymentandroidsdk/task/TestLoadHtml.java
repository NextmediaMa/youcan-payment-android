package com.canshipy.youcanpaymentandroidsdk.task;

import android.os.AsyncTask;
import android.util.Log;

import com.canshipy.youcanpaymentandroidsdk.instrafaces.PayCallBack;
import com.canshipy.youcanpaymentandroidsdk.models.Result;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestLoadHtml extends AsyncTask<String, Void, String> {

    String url;
    RequestBody formBody;
    PayCallBack onResultListener;

    public TestLoadHtml(String url, RequestBody formBody, PayCallBack listener) {
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
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            onResultListener.onPayFailure("error has occurred");
        }
        return null;
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        onResultListener.onPayFailure(response);

    }
}