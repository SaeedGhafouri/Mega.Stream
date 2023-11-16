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
import com.serpider.service.megastream.model.Film;


import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    Context context;
    List<Film> data;
    private String fromAction;

    public ItemAdapter(Context context, List<Film> data, String fromAction) {
        this.context = context;
        this.data = data;
        this.fromAction = fromAction;
    }

    @NonNull
    @Override
    public ItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_film, parent, false);
        return new ItemAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.MyViewHolder holder, int position) {

        holder.txtTitle.setText(data.get(position).getTitle_en());
        holder.txtTitle.setSelected(true);
        holder.txtYear.setText(String.valueOf(data.get(position).getYear()));
        holder.txtCountry.setText(data.get(position).getCountry());
        Glide.with(context).load(data.get(position).getPoster()).into(holder.imgPoster);

        holder.itemView.setOnClickListener(view -> {

            int item_unique = data.get(position).getId();
            SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("DETAILS_ITEM", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("ID_ITEM", item_unique);
            editor.apply();

            if (fromAction.equals("HOME")){
                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_detailsFragment);
            } else if (fromAction.equals("LIST")) {
                Navigation.findNavController(view).navigate(R.id.action_listUniqueFragment_to_detailsFragment);
            }else if (fromAction.equals("SEARCH")) {
                Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_detailsFragment);
            }

        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView txtTitle, txtYear, txtCountry;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.filmItemPoster);
            txtTitle = itemView.findViewById(R.id.filmItemTitle);
            txtYear = itemView.findViewById(R.id.filmItemYear);
            txtCountry = itemView.findViewById(R.id.filmItemCountry);
        }
    }
}