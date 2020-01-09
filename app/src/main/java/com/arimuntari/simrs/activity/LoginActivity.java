package com.arimuntari.simrs.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arimuntari.simrs.R;
import com.arimuntari.simrs.sharedPreference.*;
import com.arimuntari.simrs.config.Config;
import com.arimuntari.simrs.object.Patient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    public Boolean cekLogin = false;
    public String error = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText etCode = findViewById(R.id.etuserid);
        final EditText etPassword = findViewById(R.id.etpassword);
        Button login = findViewById(R.id.button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = etCode.getText().toString();
                String password = etPassword.getText().toString();
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.put("code", code);
                    params.put("birthdate", password);
                    String url = Config.url+"/api/login";
                    client.post(url, params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            try {
                                String result = new String(responseBody);
                                JSONObject responseObject = new JSONObject(result);
                                error = responseObject.getString("errMessage");
                                if(error.isEmpty()) {
                                    JSONObject data = responseObject.getJSONObject("data");
                                    Patient patient =new Patient();
                                    patient.setId( data.getString("id"));
                                    patient.setCode( data.getString("code"));
                                    patient.setName( data.getString("name"));
                                    patient.setBirthdate( data.getString("birthdate"));
                                    patient.setPhone_number( data.getString("phone_number"));
                                    patient.setAddress( data.getString("address"));
                                    PatientSharedPreference patientSharedPreference = new PatientSharedPreference( getApplicationContext());
                                    patientSharedPreference.setPatient(patient);
                                    Log.d("name", patient.getName());
                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                    i.putExtra("userlogin", patient);
                                    startActivity(i);
                                }else{
                                    Toast.makeText(LoginActivity.this, "Login Gagal!!!"+ error, Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(LoginActivity.this, "Login Gagal!!!"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable err) {
                            error = err.getMessage();
                        }
                    });
                }catch (Exception e){
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
