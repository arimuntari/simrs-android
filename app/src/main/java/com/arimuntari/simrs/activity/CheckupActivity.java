package com.arimuntari.simrs.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.arimuntari.simrs.R;
import com.arimuntari.simrs.adapter.CheckupAdapter;
import com.arimuntari.simrs.config.Config;
import com.arimuntari.simrs.model.CheckupModel;
import com.arimuntari.simrs.object.Checkup;
import com.arimuntari.simrs.object.CheckupDetail;
import com.arimuntari.simrs.object.Patient;

import java.util.ArrayList;

public class CheckupActivity extends AppCompatActivity {
    private ArrayList<Checkup> listCheckup = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkup);
        getSupportActionBar().setTitle("Data Checkup");

        Patient patient = Config.cekLogin(this);
        RecyclerView rvCheckup = findViewById(R.id.rv_checkup);
        rvCheckup.setLayoutManager(new LinearLayoutManager(this));
        final CheckupAdapter adapter = new CheckupAdapter();
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickCallback(new CheckupAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Checkup checkup) {
                Intent intent = new Intent(CheckupActivity.this, DetailCheckupActivity.class);
                intent.putExtra(DetailCheckupActivity.EXTRA_ITEM, checkup);
                startActivity(intent);
            }
        });
        CheckupModel checkupModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(CheckupModel.class);

        checkupModel.setCheckup(CheckupActivity.this,  patient);
        checkupModel.getListCheckup().observe(this, new Observer<ArrayList<Checkup>>() {
            @Override
            public void onChanged(ArrayList<Checkup> checkups) {
                listCheckup = checkups;
                adapter.setData(checkups);
                //showLoading(false);
            }
        });

        rvCheckup.setAdapter(adapter);
    }
}
