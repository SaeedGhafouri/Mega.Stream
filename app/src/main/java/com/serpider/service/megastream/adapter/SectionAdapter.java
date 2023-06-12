package com.serpider.service.megastream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.serpider.service.megastream.R;
import com.serpider.service.megastream.model.Season;
import com.serpider.service.megastream.model.Section;

import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.MyViewHolder> {

    Context context;
    List<Section> data;

    public SectionAdapter(Context context, List<Section> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public SectionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_section, parent, false);
        return new SectionAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionAdapter.MyViewHolder holder, int position) {
        holder.txtTitle.setText(data.get(position).getSerial_section_title());
        holder.txtQuality.setText(data.get(position).getSerial_section_quality());
        String value = data.get(position).getSerial_section_value();
        if (value.length() == 1) {
            holder.txtValue.setText("0"+value);
        }else {
            holder.txtValue.setText(value);
        }

        holder.itemView.setOnClickListener(view -> {});

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtValue, txtQuality;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.itemSectionTitle);
            txtValue = itemView.findViewById(R.id.itemSectionValue);
            txtQuality = itemView.findViewById(R.id.itemSectionQuality);
        }
    }

}
