package com.serpider.service.megastream.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.model.Country;
import com.serpider.service.megastream.model.Network;


import java.util.List;

public class NetworkAdapter extends RecyclerView.Adapter<NetworkAdapter.MyViewHolder> {

    Context context;
    List<Network> data;

    public NetworkAdapter(Context context, List<Network> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public NetworkAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_country, parent, false);
        return new NetworkAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NetworkAdapter.MyViewHolder holder, int position) {
        holder.txtTitle.setText(data.get(position).getName());
        Glide.with(context).load(data.get(position).getVector()).into(holder.imgPoster);


        SharedPreferences sharedPreferences = context.getSharedPreferences("DETAILS_ITEM", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        holder.itemView.setOnClickListener(view ->{
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_listUniqueFragment);
            editor.putString("GROUP_TITLE", data.get(position).getName());
            editor.putString("GROUP_QUERY", "item_network");
            editor.putString("GROUP_NAME", String.valueOf(data.get(position).getId()));
            editor.putString("GROUP_VECTOR", data.get(position).getVector());
            editor.apply();
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView txtTitle;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.gropingImg);
            txtTitle = itemView.findViewById(R.id.gropingTitle);
        }
    }
}
