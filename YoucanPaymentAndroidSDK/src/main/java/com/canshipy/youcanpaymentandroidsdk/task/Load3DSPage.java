package com.canshipy.youcanpaymentandroidsdk.task;

import android.os.AsyncTask;

import com.canshipy.youcanpaymentandroidsdk.YoucanPayment;
import com.canshipy.youcanpaymentandroidsdk.models.Result;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Load3DSPage extends AsyncTask<String, Void, Result> {

    String url;
    String listenerUrl;
    RequestBody formBody;

    public Load3DSPage(String url,String callback, RequestBody formBody) {
        this.url = url;
        this.formBody = formBody;
        this.listenerUrl = callback ;
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
        Result result = new Result();

        try {
            response = okHttpClient.newCall(request).execute();
            result.success = true;
            result.threeDsPage = response.body().string();
            result.listenUrl = listenerUrl;
        } catch (Exception e) {
            e.printStackTrace();
            result.message = "Load3DSPage :error has occurred";
        }

        return  result;

    }

    @Override
    protected void onPostExecute(Result response) {
        super.onPostExecute(response);

        if(response.success)
             YoucanPayment.payListener.on3DsResult(response);
        else
            YoucanPayment.payListener.onPayFailure(response.message);

    }
}