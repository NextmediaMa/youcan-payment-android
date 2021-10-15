package com.youcan.payment.task;

import android.os.AsyncTask;

import com.youcan.payment.interfaces.PayCallBackImpl;
import com.youcan.payment.models.YCPayResult;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class YCPayLoad3DSPageTask extends AsyncTask<String, Void, YCPayResult> {

    String url;
    String listenerUrl;
    RequestBody formBody;
    PayCallBackImpl payCallBack;
    public YCPayLoad3DSPageTask(String url, String callback, RequestBody formBody, PayCallBackImpl listener) {
        this.url = url;
        this.formBody = formBody;
        this.listenerUrl = callback;
        this.payCallBack = listener;
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

        return result;
    }

    @Override
    protected void onPostExecute(YCPayResult response) {
        super.onPostExecute(response);

        if (response.success) {
            payCallBack.on3DsResult(response);
        } else {
            payCallBack.onPayFailure(response.message);
        }
    }
}