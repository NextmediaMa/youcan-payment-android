package com.canshipy.youcanpaymentandroidsdk.task;

import android.os.AsyncTask;

import com.canshipy.youcanpaymentandroidsdk.YCPay;
import com.canshipy.youcanpaymentandroidsdk.models.YCPayResult;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class YCPayLoad3DSPageTask extends AsyncTask<String, Void, YCPayResult> {

    String url;
    String listenerUrl;
    RequestBody formBody;

    public YCPayLoad3DSPageTask(String url, String callback, RequestBody formBody) {
        this.url = url;
        this.formBody = formBody;
        this.listenerUrl = callback ;
    }

    @Override
    protected YCPayResult doInBackground(String... strings) {

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("Accept", "application/json")
                .url(url)
                .post(formBody)
                .build();
        Response response;
        YCPayResult result = new YCPayResult();

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
    protected void onPostExecute(YCPayResult response) {
        super.onPostExecute(response);

        if(response.success)
             YCPay.payListener.on3DsResult(response);
        else
            YCPay.payListener.onPayFailure(response.message);

    }
}