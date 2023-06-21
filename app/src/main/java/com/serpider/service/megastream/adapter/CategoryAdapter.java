package com.serpider.service.megastream.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.serpider.service.megastream.R;
import com.serpider.service.megastream.model.Genre;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    Context context;
    List<Genre> data;

    public CategoryAdapter(Context context, List<Genre> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new CategoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {
        holder.txtTitle.setText(data.get(position).getGenre_name());

        SharedPreferences sharedPreferences = context.getSharedPreferences("DETAILS_ITEM", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        holder.itemView.setOnClickListener(view ->{
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_listUniqueFragment);
            editor.putString("GROUP_TYPE", "item_genre");
            editor.putString("GROUP_NAME", data.get(position).getGenre_name());
            editor.putString("GROUP_VECTOR", "");
            editor.apply();
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.categoryTitle);
        }
    }
}
