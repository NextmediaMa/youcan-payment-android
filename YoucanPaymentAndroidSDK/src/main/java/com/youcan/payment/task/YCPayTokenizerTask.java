package com.youcan.payment.task;

import android.os.AsyncTask;
import android.util.Log;

import com.youcan.payment.instrafaces.YCPayTokenizerCallBackImpl;
import com.youcan.payment.models.YCPayResult;
import com.youcan.payment.models.YCPayToken;
import com.youcan.payment.models.YCPayTokenizerParams;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class YCPayTokenizerTask extends AsyncTask<String, Void, YCPayResult> {

    YCPayTokenizerParams params;
    Request.Builder requestBuilder;
    YCPayTokenizerCallBackImpl onResultListener;
    Request request;

    public YCPayTokenizerTask(YCPayTokenizerParams params, RequestBody formBody, YCPayTokenizerCallBackImpl listenerToken) {
        initRequest(params, formBody);
        this.onResultListener = listenerToken;
    }

    void initRequest(YCPayTokenizerParams params, RequestBody formBody) {
        Iterator hmIterator = params.getHeader().entrySet().iterator();

        this.params = params;
        this.requestBuilder = new Request.Builder();
        this.requestBuilder.url(params.getTokenizerUrl())
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
    protected YCPayResult doInBackground(String... strings) {

        OkHttpClient okHttpClient = new OkHttpClient();

        Response response;
        String result;

        try {
            response = okHttpClient.newCall(request).execute();
            result = response.body().string();
            if(response.isSuccessful()) {
                YCPayToken token = new YCPayToken().tokenFromJson(result);
                Log.e("Response", "doInBackground: "+result );
                onResultListener.onResponse(token);

                return null;
            }
            JSONObject jsonResult = new JSONObject(result);
            onResultListener.onError(jsonResult.getString("message"));
            return  null;
        } catch (Exception e) {
            e.printStackTrace();

        }

        onResultListener.onError("error");
        return null;

    }

    @Override
    protected void onPostExecute(YCPayResult result) {
        super.onPostExecute(result);

    }
}