package com.arimuntari.simrs.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arimuntari.simrs.R;
import com.arimuntari.simrs.adapter.CheckupAdapter;
import com.arimuntari.simrs.adapter.CheckupDetailAdapter;
import com.arimuntari.simrs.adapter.CheckupDiagnosaAdapter;
import com.arimuntari.simrs.model.CheckupDetailModel;
import com.arimuntari.simrs.model.CheckupModel;
import com.arimuntari.simrs.object.Checkup;
import com.arimuntari.simrs.object.CheckupDetail;

import java.util.ArrayList;

public class DetailCheckupActivity extends AppCompatActivity {
    public static final String EXTRA_ITEM = "STATE_EXTRA";
    private ArrayList<CheckupDetail> listDiagnosis = new ArrayList<>();
    private ArrayList<CheckupDetail> listActions = new ArrayList<>();
    private ArrayList<CheckupDetail> listMedicines = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_checkup);
        getSupportActionBar().setTitle("Data Detail Checkup");
        final Checkup checkup = getIntent().getParcelableExtra(EXTRA_ITEM);
        CheckupDetailModel checkupDetailModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(CheckupDetailModel.class);
        checkupDetailModel.setData(this, checkup.getId());

        TextView tvDate = findViewById(R.id.tv_date);
        TextView tvTime = findViewById(R.id.tv_time);
        TextView tvTotal = findViewById(R.id.tv_total);

        tvDate.setText(checkup.getDateRegister());
        tvTime.setText(checkup.getTime());
        tvTotal.setText("Rp."+ checkup.getPriceTotal());
        RecyclerView rvDiagnosa = findViewById(R.id.rv_diagnosa);
        final LinearLayout linDiganosa = findViewById(R.id.ll_list_diagnosa);
        rvDiagnosa.setLayoutManager(new LinearLayoutManager(this));
        final CheckupDiagnosaAdapter adapter = new CheckupDiagnosaAdapter();
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickCallback(new CheckupDiagnosaAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(CheckupDetail checkup) {

            }
        });
        checkupDetailModel.getListDiagnosis().observe(this, new Observer<ArrayList<CheckupDetail>>() {
            @Override
            public void onChanged(ArrayList<CheckupDetail> checkups) {
                listDiagnosis = checkups;
                adapter.setData(checkups);
                if(checkups.size() == 0){
                    linDiganosa.setVisibility(View.GONE);
                }
            }
        });
        rvDiagnosa.setAdapter(adapter);


        RecyclerView rvAction = findViewById(R.id.rv_action);
        final LinearLayout linAction = findViewById(R.id.ll_list_action);
        rvAction.setLayoutManager(new LinearLayoutManager(this));
        final CheckupDetailAdapter adapterAction = new CheckupDetailAdapter();
        adapterAction.notifyDataSetChanged();
        adapterAction.setOnItemClickCallback(new CheckupDetailAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(CheckupDetail checkup) {

            }
        });

        checkupDetailModel.getListAction().observe(this, new Observer<ArrayList<CheckupDetail>>() {
            @Override
            public void onChanged(ArrayList<CheckupDetail> checkups) {
                listActions = checkups;
                adapterAction.setData(checkups, "action");
                if(checkups.size() == 0){
                    linAction.setVisibility(View.GONE);
                }
            }
        });
        rvAction.setAdapter(adapterAction);

        RecyclerView rvMedicine = findViewById(R.id.rv_medicine);
        final LinearLayout linMedicine = findViewById(R.id.ll_list_medicine);
        rvMedicine.setLayoutManager(new LinearLayoutManager(this));
        final CheckupDetailAdapter adapterMedicine = new CheckupDetailAdapter();
        adapterMedicine.notifyDataSetChanged();
        adapterMedicine.setOnItemClickCallback(new CheckupDetailAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(CheckupDetail checkup) {

            }
        });
        checkupDetailModel.getListMedicine().observe(this, new Observer<ArrayList<CheckupDetail>>() {
            @Override
            public void onChanged(ArrayList<CheckupDetail> checkups) {
                listMedicines = checkups;
                adapterMedicine.setData(checkups, "medicine");
                if(checkups.size() == 0){
                    linMedicine.setVisibility(View.GONE);
                }
            }
        });
        rvMedicine.setAdapter(adapterMedicine);
    }
}
