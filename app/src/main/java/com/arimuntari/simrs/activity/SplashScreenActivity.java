package com.arimuntari.simrs.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.arimuntari.simrs.R;
import com.arimuntari.simrs.sharedPreference.PatientSharedPreference;
import com.arimuntari.simrs.object.Patient;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            PatientSharedPreference patientSharedPreference = new PatientSharedPreference(getApplicationContext());
            Patient patient = patientSharedPreference.getPatient();
            if(patient.getBirthdate().isEmpty()){
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(i);
            }else{
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);
            }
            }
        },2000);
    }
}
