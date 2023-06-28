package com.serpider.service.megastream.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.serpider.service.megastream.R;
import com.serpider.service.megastream.model.Genre;
import com.serpider.service.megastream.util.Connection;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.MyViewHolder> {
    Context context;
    List<Genre> data;
    FragmentActivity activity;

    public GenreAdapter(Context context, List<Genre> data, FragmentActivity activity) {
        this.context = context;
        this.data = data;
        this.activity = activity;
    }

    @NonNull
    @Override
    public GenreAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_genre, parent, false);
        return new GenreAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreAdapter.MyViewHolder holder, int position) {
        holder.txtTitle.setText(data.get(position).getGenre_name());

        SharedPreferences sharedPreferences = activity.getSharedPreferences("DETAILS_ITEM", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        holder.itemView.setOnClickListener(view ->{
            if (new Connection().isNetwork(activity)) {
                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_listUniqueFragment);
                editor.putString("GROUP_TYPE", "item_genre");
                editor.putString("GROUP_NAME", data.get(position).getGenre_name());
                editor.putString("GROUP_VECTOR", "");
                editor.apply();
            }else {
                new Connection().showDialog(activity);
            }
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
            txtTitle = itemView.findViewById(R.id.genreTitle);
        }
    }
}
