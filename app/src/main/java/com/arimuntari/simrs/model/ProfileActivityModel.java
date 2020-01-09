package com.arimuntari.simrs.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.arimuntari.simrs.activity.MainActivity;
import com.arimuntari.simrs.config.Config;
import com.arimuntari.simrs.object.Patient;
import com.arimuntari.simrs.sharedPreference.PatientSharedPreference;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivityModel extends ViewModel {
    public void updatePatient(final Context context, Patient patient) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            String url = Config.url + "/api/patient/update";
            RequestParams params = new RequestParams();
            params.put("patient_id", patient.getId());
            params.put("code", patient.getCode());
            params.put("name", patient.getName());
            params.put("birthdate", patient.getBirthdate());
            params.put("phone_number", patient.getPhone_number());
            params.put("address", patient.getAddress());
            client.post(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String result = new String(responseBody);
                        JSONObject responseObject = new JSONObject(result);
                        String error = responseObject.getString("errMessage");
                        if(error.isEmpty()) {
                            JSONObject data = responseObject.getJSONObject("data");
                            Log.d("data", data.toString());
                            Patient pat= new Patient();
                            pat.setId( data.getString("id"));
                            pat.setCode( data.getString("code"));
                            pat.setName( data.getString("name"));
                            pat.setBirthdate( data.getString("birthdate"));
                            pat.setPhone_number( data.getString("phone_number"));
                            pat.setAddress( data.getString("address"));
                            PatientSharedPreference patientSharedPreference = new PatientSharedPreference( context);
                            patientSharedPreference.setPatient(pat);
                            Intent i = new Intent(context, MainActivity.class);
                            context.startActivity(i);
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "Update data gagal!"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                    Toast.makeText(context, "Update data gagal!!"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Data Tidak Ditemukan!!"+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
