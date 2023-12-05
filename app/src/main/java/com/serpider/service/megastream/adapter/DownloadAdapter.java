package com.serpider.service.megastream.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.serpider.service.megastream.R;
import com.serpider.service.megastream.model.Download;
import com.serpider.service.megastream.model.Genre;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.MyViewHolder>{
    private FragmentActivity activity;
    private List<Download> data;

    public DownloadAdapter(FragmentActivity activity, List<Download> data) {
        this.activity = activity;
        this.data = data;
    }

    @NonNull
    @Override
    public DownloadAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_download, parent, false);
        return new DownloadAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Download download = data.get(position);
        holder.txtTitle.setText(download.getTitle());
    }

    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.genreTitle);
        }
    }
}
