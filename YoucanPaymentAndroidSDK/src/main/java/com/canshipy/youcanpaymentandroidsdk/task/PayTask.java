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
            Result resultObject = new Result().resultFromJson(result.toString());
            Log.e("build_test",  resultObject.toString());

            if(!result.has("success")) {
                resultObject.is3DS = true;
                onResultListener.onPayFailure("payment with 3DS");

                return null;
            }

            if(resultObject.success) {
                onResultListener.onPaySuccess(resultObject);
            } else {
                onResultListener.onPayFailure(resultObject.message);
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
            onResultListener.onPayFailure("error has occurred");
        }
        return null;
    }

}