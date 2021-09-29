package com.youcan.payment.task;

import android.os.AsyncTask;
import android.util.Log;

import com.youcan.payment.YCPay;
import com.youcan.payment.instrafaces.PayCallBack;
import com.youcan.payment.models.YCPayResult;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PayTask extends AsyncTask<String, Void, YCPayResult> {

    String url;
    RequestBody formBody;
    PayCallBack onResultListener;

    public PayTask(String url, RequestBody formBody, PayCallBack listener) {
        this.url = url;
        this.formBody = formBody;
        this.onResultListener = listener;
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
        JSONObject result;

        try {
            response = okHttpClient.newCall(request).execute();
            result = new JSONObject(response.body().string());
            YCPayResult resultObject = new YCPayResult().resultFromJson(result.toString());
            Log.e("build_test",  result.toString());
            if(!response.isSuccessful()){
                YCPayResult result1 = new YCPayResult();
                result1.message = result.getString("message");
                return result1;
            }
            if(!result.has("success")) {
                resultObject.is3DS = true;
             //   YoucanPayment.load3DsPage(resultObject);
            }

            return resultObject;
        } catch (Exception e) {
            e.printStackTrace();
            YCPayResult resultObject = new YCPayResult();
            resultObject.message = "error has occurred";
            resultObject.success =false;

            return resultObject;

        }
    }

    @Override
    protected void onPostExecute(YCPayResult result) {
        super.onPostExecute(result);

        if(result.is3DS ) {
           YCPay.load3DsPage(result);
            return;
        }

        if(result.success) {
          YCPay.payListener.onPaySuccess(result);
        } else {
          YCPay.payListener.onPayFailure(result.message);
        }

    }
}