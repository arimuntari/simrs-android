package com.arimuntari.simrs.config;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.arimuntari.simrs.activity.LoginActivity;
import com.arimuntari.simrs.object.Patient;
import com.arimuntari.simrs.sharedPreference.PatientSharedPreference;

public class Config {
    public static String  url = "http://simrs.arimuntari.site";
    public static Patient cekLogin(Context context){
        PatientSharedPreference patientSharedPreference = new PatientSharedPreference(context);
        Patient patient = patientSharedPreference.getPatient();
        if(patient == null){
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
        }

        return patient;
    }
    public static void Logout(Context context) {
        PatientSharedPreference patientSharedPreference = new PatientSharedPreference(context);
        patientSharedPreference.clear();
        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
    }
}
