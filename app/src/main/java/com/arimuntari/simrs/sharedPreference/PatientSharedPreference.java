package com.arimuntari.simrs.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.arimuntari.simrs.object.Patient;

public class PatientSharedPreference {
    private static final String PREFS_NAME = "patient_prefs";
    private static final String ID = "id";
    private static final String CODE = "code";
    private static final String NAME = "name";
    private static final String BIRTHDATE = "birthdate";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String ADDRESS = "address";
    private SharedPreferences preferences;

    public PatientSharedPreference(Context context){
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setPatient(Patient patient) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ID, patient.getId());
        editor.putString(CODE, patient.getCode());
        editor.putString(NAME, patient.getName());
        editor.putString(BIRTHDATE, patient.getBirthdate());
        editor.putString(PHONE_NUMBER, patient.getPhone_number());
        editor.putString(ADDRESS, patient.getAddress());
        editor.apply();
    }
    public Patient getPatient() {
        Patient model = new Patient();
        model.setId(preferences.getString(ID, ""));
        model.setCode(preferences.getString(CODE, ""));
        model.setName(preferences.getString(NAME, ""));
        model.setBirthdate(preferences.getString(BIRTHDATE, ""));
        model.setPhone_number(preferences.getString(PHONE_NUMBER, ""));
        model.setAddress(preferences.getString(ADDRESS, ""));
        return model;
    }
    public void clear() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
