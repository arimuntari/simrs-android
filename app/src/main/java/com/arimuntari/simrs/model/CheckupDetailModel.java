package com.arimuntari.simrs.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.arimuntari.simrs.config.Config;
import com.arimuntari.simrs.object.Checkup;
import com.arimuntari.simrs.object.CheckupDetail;
import com.arimuntari.simrs.object.Patient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class CheckupDetailModel extends ViewModel {
    private MutableLiveData<ArrayList<CheckupDetail>> listDiagnosis= new MutableLiveData();
    private MutableLiveData<ArrayList<CheckupDetail>> listAction= new MutableLiveData();
    private MutableLiveData<ArrayList<CheckupDetail>> listMedicine= new MutableLiveData();
    public void setData(final Context context, String registerId){
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            String url = Config.url + "/api/exam/detail";
            RequestParams params = new RequestParams();
            params.put("id", registerId);
            client.post(url, params,  new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String result = new String(responseBody);
                        JSONObject responseObject = new JSONObject(result);
                        JSONObject data = responseObject.getJSONObject("data");
                        JSONArray diagnosa = data.getJSONArray("diagnosis");
                        ArrayList<CheckupDetail> listDiagnosa = new ArrayList<>();
                        for (int i = 0; i < diagnosa.length(); i++) {
                            JSONObject checkups = diagnosa.getJSONObject(i);
                            CheckupDetail diagnosis = new CheckupDetail();
                            diagnosis.setName(checkups.getString("name"));
                            listDiagnosa.add(diagnosis);
                        }
                        listDiagnosis.postValue(listDiagnosa);


                        JSONArray action = data.getJSONArray("action");
                        ArrayList<CheckupDetail> listActions = new ArrayList<>();
                        for (int i = 0; i < action.length(); i++) {
                            JSONObject checkups = action.getJSONObject(i);
                            CheckupDetail actions = new CheckupDetail();
                            actions.setName(checkups.getString("name"));
                            actions.setPrice(checkups.getString("price"));
                            listActions.add(actions);
                        }
                        listAction.postValue(listActions);


                        JSONArray medicine = data.getJSONArray("medicine");
                        ArrayList<CheckupDetail> listMedicines = new ArrayList<>();
                        for (int i = 0; i < medicine.length(); i++) {
                            JSONObject checkups = medicine.getJSONObject(i);
                            CheckupDetail medicines = new CheckupDetail();
                            medicines.setName(checkups.getString("name"));
                            medicines.setPrice(checkups.getString("price"));
                            medicines.setQty(checkups.getString("amount"));
                            medicines.setTotal(checkups.getString("total"));
                            listMedicines.add(medicines);
                        }
                        listMedicine.postValue(listMedicines);
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
    public MutableLiveData<ArrayList<CheckupDetail>> getListDiagnosis(){
        return listDiagnosis;
    }

    public MutableLiveData<ArrayList<CheckupDetail>> getListAction(){
        return listAction;
    }

    public MutableLiveData<ArrayList<CheckupDetail>> getListMedicine(){
        return listMedicine;
    }
}
