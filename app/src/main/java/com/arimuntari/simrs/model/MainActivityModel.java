package com.arimuntari.simrs.model;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.arimuntari.simrs.config.Config;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivityModel extends ViewModel {
    private MutableLiveData<String> queue = new MutableLiveData<>();
    private MutableLiveData<String> myqueue = new MutableLiveData<>();
    private MutableLiveData<String> total = new MutableLiveData<>();
    private MutableLiveData<String> status = new MutableLiveData<>();
    public void setAntrian(final Context context){
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            String url = Config.url + "/api/register/queue";
            client.post(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String result = new String(responseBody);
                        JSONObject responseObject = new JSONObject(result);
                        queue.postValue(responseObject.getString("data"));
                    } catch (Exception e) {
                        Toast.makeText(context, "Data Tidak Ditemukan!!"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                    Toast.makeText(context, "Data Tidak Ditemukan!!"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Data Tidak Ditemukan!!"+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public MutableLiveData<String> getAntrian(){
        return queue;
    }
    public void setMyqueue(final Context context, String patientId){
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            String url = Config.url + "/api/register/myqueue";
            RequestParams params = new RequestParams();
            params.put("patient_id", patientId);
            client.post(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String result = new String(responseBody);
                        JSONObject responseObject = new JSONObject(result);
                        String error = responseObject.getString("errMessage");
                        if(error.isEmpty()) {
                            myqueue.postValue(responseObject.getString("data"));
                        }else{
                            myqueue.postValue("");
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "Data Tidak Ditemukan!!"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                    Toast.makeText(context, "Data Tidak Ditemukan!!"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Data Tidak Ditemukan!!"+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public MutableLiveData<String> getMyqueue(){
        return myqueue;
    }
    public void setTotal(String patientId, final Context context){
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            String url = Config.url + "/api/exam/total";
            RequestParams params = new RequestParams();
            params.put("patient_id", patientId);
            client.post(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String result = new String(responseBody);
                        JSONObject responseObject = new JSONObject(result);
                        total.postValue(responseObject.getString("data"));
                    } catch (Exception e) {
                        Toast.makeText(context, "Data Tidak Ditemukan!!"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                    Toast.makeText(context, "Data Tidak Ditemukan!!"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Data Tidak Ditemukan!!"+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public MutableLiveData<String> getTotal(){
        return total;
    }
    public void register(final Context context, String patientId){
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            String url = Config.url + "/api/register";
            RequestParams params = new RequestParams();
            params.put("patient_id", patientId);
            client.post(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String result = new String(responseBody);
                        JSONObject responseObject = new JSONObject(result);
                        String error = responseObject.getString("errMessage");
                        status.postValue(error);
                    } catch (Exception e) {
                        Toast.makeText(context, "Data Tidak Ditemukan!!"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                    Toast.makeText(context, "Data Tidak Ditemukan!!"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Data Tidak Ditemukan!!"+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public MutableLiveData<String> getRegister(){
        return status;
    }
}
