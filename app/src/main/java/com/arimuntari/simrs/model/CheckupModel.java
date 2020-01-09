package com.arimuntari.simrs.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.arimuntari.simrs.config.Config;
import com.arimuntari.simrs.object.Checkup;
import com.arimuntari.simrs.object.Patient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class CheckupModel extends ViewModel {
    private MutableLiveData<ArrayList<Checkup>> listCheckup = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Checkup>> getListCheckup() {
        return listCheckup;
    }

    public void setCheckup(final Context context, Patient patient){
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            final ArrayList<Checkup> listItems = new ArrayList<>();
            String url = Config.url + "/api/exam/view";
            RequestParams params = new RequestParams();
            params.put("patient_id", patient.getId());
            client.post(url, params,  new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String result = new String(responseBody);
                        JSONObject responseObject = new JSONObject(result);
                        JSONArray list = responseObject.getJSONArray("data");
                        Log.d("result", list.toString());
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject checkups = list.getJSONObject(i);
                            Checkup checkup = new Checkup();
                            checkup.setId(checkups.getString("id"));
                            checkup.setNoRegister(checkups.getString("noRegister"));
                            checkup.setDateRegister(checkups.getString("dateRegister"));
                            checkup.setTime(checkups.getString("time"));
                            checkup.setPriceTotal(checkups.getString("priceTotal"));
                            listItems.add(checkup);
                        }
                        listCheckup.postValue(listItems);
                    } catch (Exception e) {
                        Toast.makeText(context, "Data Kosong!!"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                    Toast.makeText(context, "Internet tidak terhubung!!"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Data Tidak Ditemukan!!"+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
