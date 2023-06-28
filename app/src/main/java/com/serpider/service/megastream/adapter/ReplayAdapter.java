package com.serpider.service.megastream.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.interfaces.Elements;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ReplayAdapter extends RecyclerView.Adapter<ReplayAdapter.MyViewHolder> {
    Context context;
    List<Replay> data;
    FragmentActivity activity;
    public ReplayAdapter(Context context, List<Replay> data, FragmentActivity activity) {
        this.context = context;
        this.data = data;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ReplayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_replay, parent, false);
        return new ReplayAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplayAdapter.MyViewHolder holder, int position) {
        holder.txtNick.setText(data.get(position).getUser_nickname());
        holder.txtUsername.setText("@" + data.get(position).getUser_name());
        holder.txtMsg.setText(data.get(position).getReplay_msg());
        holder.txtDate.setText(data.get(position).getReplay_date());
        Picasso.get().load(data.get(position).getUser_vector()).into(holder.imgVector);
        holder.imgVector.setOnClickListener(view -> Elements.DialogPreImage(activity, data.get(position).getUser_vector()));
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgVector;
        TextView txtNick, txtUsername, txtDate, txtMsg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNick = itemView.findViewById(R.id.txtReplayNick);
            txtUsername = itemView.findViewById(R.id.txtReplayUsername);
            txtDate = itemView.findViewById(R.id.txtReplayDate);
            txtMsg = itemView.findViewById(R.id.txtReplayMsg);
            imgVector = itemView.findViewById(R.id.imgReplayVector);

        }
    }


}
