package com.arimuntari.simrs.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arimuntari.simrs.R;
import com.arimuntari.simrs.object.Checkup;
import com.arimuntari.simrs.object.CheckupDetail;

import java.util.ArrayList;

public class CheckupAdapter extends RecyclerView.Adapter<CheckupAdapter.ViewHolder> {
    private ArrayList<Checkup> listCheckup = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public void setData(ArrayList<Checkup> checkups){
        listCheckup.clear();
        listCheckup.addAll(checkups);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_checkup, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ViewHolder.bind(listCheckup.get(position), holder);
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
        static TextView tvNoRegister, tvTitle, tvDescribe;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoRegister = itemView.findViewById(R.id.tv_no_register);
            tvTitle = itemView.findViewById(R.id.tv_item_name);
            tvDescribe = itemView.findViewById(R.id.tv_item_describ);
        }

        public static void bind(Checkup checkup, ViewHolder holder) {
            tvNoRegister.setText(checkup.getNoRegister());
            tvTitle.setText(checkup.getDateRegister() +"  "+ checkup.getTime());
            tvDescribe.setText("Rp. "+checkup.getPriceTotal());
        }
    }
    @Override
    public int getItemViewType(int position)
    {
        return position;
    }
    public interface OnItemClickCallback {
        void onItemClicked(Checkup checkup);
    }
}
