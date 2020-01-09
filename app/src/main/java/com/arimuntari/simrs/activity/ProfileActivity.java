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
import com.arimuntari.simrs.config.Config;
import com.arimuntari.simrs.model.ProfileActivityModel;
import com.arimuntari.simrs.object.Patient;
import com.arimuntari.simrs.sharedPreference.PatientSharedPreference;

public class ProfileActivity extends AppCompatActivity {
    private ProfileActivityModel profileActivityModel;
    private Patient patient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Edit Profile");
        profileActivityModel = new ProfileActivityModel();
        patient = Config.cekLogin(this);

        final EditText etname = findViewById(R.id.txtname);
        final EditText etbirthdate = findViewById(R.id.txtbirthdate);
        final EditText etphone = findViewById(R.id.txtphone);
        final EditText etaddress = findViewById(R.id.txtaddress);
        Button btnsave = findViewById(R.id.btn_edit_profile);
        etname.setText(patient.getName());
        etbirthdate.setText(patient.getBirthdate());
        etphone.setText(patient.getPhone_number());
        etaddress.setText(patient.getAddress());

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Patient pat = new Patient();
                pat.setId(patient.getId());
                pat.setCode(patient.getCode());
                String name = etname.getText().toString();
                String birthdate = etbirthdate.getText().toString();
                String phone = etphone.getText().toString();
                String address = etaddress.getText().toString();
                if(name.isEmpty() || birthdate.isEmpty()){
                    Toast.makeText(ProfileActivity.this, "NAME and BIRTHDATE is required", Toast.LENGTH_SHORT).show();
                }else {
                    pat.setName(name);
                    pat.setBirthdate(birthdate);
                    pat.setPhone_number(phone);
                    pat.setAddress(address);
                    profileActivityModel.updatePatient(ProfileActivity.this, pat);
                }
            }
        });
    }
}
