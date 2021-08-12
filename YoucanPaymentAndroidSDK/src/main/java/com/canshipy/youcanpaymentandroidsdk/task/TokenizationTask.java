package com.canshipy.youcanpaymentandroidsdk.task;

import android.os.AsyncTask;
import android.util.Log;

import com.canshipy.youcanpaymentandroidsdk.instrafaces.TokenizationCallBack;
import com.canshipy.youcanpaymentandroidsdk.models.Token;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TokenizationTask extends AsyncTask<String, Void, String> {

    String url;
    RequestBody formBody;
    TokenizationCallBack onResultListener;

    public TokenizationTask(String url, RequestBody formBody, TokenizationCallBack listener) {
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
        Log.e("build_test", url );

        try {
            response = okHttpClient.newCall(request).execute();
            result = new JSONObject(response.body().string());
            Log.e("build_test", result.toString() );

            if(result.has("success")) {
                onResultListener.onError(result.getString("message"));

                return  null;
            }

            Token token = new Token().tokenFromJson(result.getJSONObject("token").toString());

            onResultListener.onResponse(token);
            return  null;

        } catch (Exception e) {
            e.printStackTrace();
            onResultListener.onError("error has occurred");

        }
        return null;
    }

}