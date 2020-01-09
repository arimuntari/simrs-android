package com.arimuntari.simrs.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arimuntari.simrs.R;
import com.arimuntari.simrs.config.Config;
import com.arimuntari.simrs.model.MainActivityModel;
import com.arimuntari.simrs.sharedPreference.PatientSharedPreference;
import com.arimuntari.simrs.object.Patient;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private TextView tvantrian, tvtotal, tvdaftar;
    private Patient patient;
    private FloatingActionButton btndaftar;
    private MaterialCardView cartDaftar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        patient = Config.cekLogin(this);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Dashboard");
        getSupportActionBar().setElevation(5);
        TextView tvname = findViewById(R.id.tv_name);
        TextView tvdate = findViewById(R.id.tv_date);
        Button btnedit = findViewById(R.id.btn_edit);
        Button btncheckup = findViewById(R.id.btn_list);
        cartDaftar = findViewById(R.id.cartDaftar);
        btndaftar = findViewById(R.id.btn_daftar);
        tvantrian = findViewById(R.id.tv_antrian);
        tvtotal = findViewById(R.id.tv_total);
        tvdaftar = findViewById(R.id.tv_daftar);

        tvname.setText(patient.getName());
        tvdate.setText(patient.getBirthdate());
        loadAntrian();
        loadTotal();
        loadMyqueue();
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                loadAntrian();
                handler.postDelayed(this,6000);
            }
        };
        handler.postDelayed(runnable,6000);

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });
        btncheckup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataCheckup();
            }
        });
        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    public void loadAntrian(){
        MainActivityModel mainActivityModel = new ViewModelProvider(MainActivity.this, new ViewModelProvider.NewInstanceFactory()).get(MainActivityModel.class);
        mainActivityModel.setAntrian(getApplicationContext());
        mainActivityModel.getAntrian().observe(MainActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String antrian){
                tvantrian.setText(antrian);
            }
        });
    }
    public void loadTotal(){
        String patientId = patient.getId();
        MainActivityModel mainActivityModel = new ViewModelProvider(MainActivity.this, new ViewModelProvider.NewInstanceFactory()).get(MainActivityModel.class);
        mainActivityModel.setTotal(patientId, getApplicationContext());
        mainActivityModel.getTotal().observe(MainActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String total){
                tvtotal.setText(total);
            }
        });
    }

    public void register(){
        String patientId = patient.getId();
        MainActivityModel mainActivityModel = new ViewModelProvider(MainActivity.this, new ViewModelProvider.NewInstanceFactory()).get(MainActivityModel.class);
        mainActivityModel.register(getApplicationContext(), patientId);
        mainActivityModel.getRegister().observe(MainActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String status){
               if(status.isEmpty()){
                   loadMyqueue();
               }
            }
        });
    }

    public void loadMyqueue(){
        String patientId = patient.getId();
        MainActivityModel mainActivityModel = new ViewModelProvider(MainActivity.this, new ViewModelProvider.NewInstanceFactory()).get(MainActivityModel.class);
        mainActivityModel.setMyqueue( getApplicationContext(), patientId);
        mainActivityModel.getMyqueue().observe(MainActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String antrian){
                if(antrian.isEmpty()){
                    btndaftar.show();
                    cartDaftar.setVisibility(View.INVISIBLE);
                }else {
                    btndaftar.hide();
                    cartDaftar.setVisibility(View.VISIBLE);
                    tvdaftar.setText(antrian);
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        menu.findItem(R.id.item_dashboard).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_logout:
                Config.Logout(this);
                break;
            case R.id.item_profile:
                editProfile();
                break;
            case R.id.item_checkup:
                dataCheckup();
                break;
            default:
                Toast.makeText(this, "Menu Belum Aktive", Toast.LENGTH_SHORT );
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void editProfile(){
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }
    public void dataCheckup(){
        Intent i = new Intent(this, CheckupActivity.class);
        startActivity(i);
    }
    @Override
    public void onBackPressed() {

    }


}
