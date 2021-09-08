package com.canshipy.youcanpaymentandroidsdk.task;

import android.os.AsyncTask;
import android.util.Log;

import com.canshipy.youcanpaymentandroidsdk.instrafaces.TokenizationCallBack;
import com.canshipy.youcanpaymentandroidsdk.models.Result;
import com.canshipy.youcanpaymentandroidsdk.models.Token;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TokenizationTask extends AsyncTask<String, Void, Result> {

    String url;
    RequestBody formBody;
    TokenizationCallBack onResultListener;
    public TokenizationTask(String url, RequestBody formBody, TokenizationCallBack listenerToken) {
        this.url = url;
        this.formBody = formBody;
        this.onResultListener = listenerToken;
    }

    @Override
    protected Result doInBackground(String... strings) {

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("Accept", "application/json")
                .url(url)
                .post(formBody)
                .build();
        Response response;
        JSONObject result;
        Log.e("build_test", url );
        Result objectResult= new Result();

        try {
            response = okHttpClient.newCall(request).execute();
            result = new JSONObject(response.body().string());
            Log.e("build_test", result.toString() );

            if(result.has("success")) {
                objectResult.message = result.getString("message");

                return  objectResult;
            }

            Token token = new Token().tokenFromJson(result.getJSONObject("token").toString());
            objectResult.success = true;
            onResultListener.onResponse(token);

            return  objectResult;
        } catch (Exception e) {
            e.printStackTrace();
            objectResult.message = "Token: error has occurred";
        }

        return new Result();
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        if(!result.success){
            onResultListener.onError(result.message);
        }
    }
}