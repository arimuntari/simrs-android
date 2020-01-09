package com.arimuntari.simrs.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arimuntari.simrs.R;
import com.arimuntari.simrs.object.CheckupDetail;

import java.util.ArrayList;

public class CheckupDetailAdapter extends  RecyclerView.Adapter<CheckupDetailAdapter.ViewHolder> {
    private ArrayList<CheckupDetail> listCheckup = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;
    private String type;

    public void setData(ArrayList<CheckupDetail> checkups, String type){
        listCheckup.clear();
        listCheckup.addAll(checkups);
        this.type = type;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_checkup_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.bind(listCheckup.get(position), holder, type);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listCheckup.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCheckup.size();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        static TextView tvName, tvDescribe;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvDescribe = itemView.findViewById(R.id.tv_item_describ);
        }

        public static void bind(CheckupDetail checkup, ViewHolder holder, String type) {
            if(type.equals("action")){
                tvName.setText(checkup.getName());
                tvDescribe.setText(checkup.getPrice());
            }else {
                tvName.setText(checkup.getName());
                tvDescribe.setText(checkup.getPrice()+" X "+ checkup.getQty() +" = "+checkup.getTotal());
            }
        }
    }
    @Override
    public int getItemViewType(int position)
    {
        return position;
    }
    public interface OnItemClickCallback {
        void onItemClicked(CheckupDetail checkup);
    }
}
