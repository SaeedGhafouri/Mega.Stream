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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.interfaces.Elements;
import com.serpider.service.megastream.interfaces.Key;
import com.serpider.service.megastream.model.Comment;
import com.serpider.service.megastream.model.Replay;
import com.serpider.service.megastream.util.SnackBoard;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    Context context;
    List<Comment> data;
    FragmentActivity activity;
    ApiInterFace requestReplay;
    ReplayAdapter replayAdapter;
    List<Replay> listReplay = new ArrayList<>();

    public CommentAdapter(Context context, List<Comment> data, FragmentActivity activity) {
        this.context = context;
        this.data = data;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CommentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.MyViewHolder holder, int position) {
        holder.txtNick.setText(data.get(position).getU_nickname());
        holder.txtUsername.setText("@" + data.get(position).getU_username());
        holder.txtMsg.setText(data.get(position).getMessage());
        holder.txtDate.setText(data.get(position).getDate());
        Glide.with(context).load(data.get(position).getU_vector()).into(holder.imgVector);

        holder.btnReply.setOnClickListener(view -> loadReplay(data.get(position).getId() , data.get(position).getU_nickname(), data.get(position).getU_username()));
        holder.imgVector.setOnClickListener(view -> Elements.DialogPreImage(activity, data.get(position).getU_vector()));

        if (data.get(position).getReply_count() == 0){
            holder.btnReply.setText("Replay");
        }else {
            holder.btnReply.setText(data.get(position).getReply_count() + " Replay");
        }


    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgVector;
        TextView txtNick, txtUsername, txtDate, txtMsg, btnReply;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNick = itemView.findViewById(R.id.txtCommentNick);
            txtUsername = itemView.findViewById(R.id.txtCommentUsername);
            txtDate = itemView.findViewById(R.id.txtCommentDate);
            txtMsg = itemView.findViewById(R.id.txtCommentMsg);
            btnReply = itemView.findViewById(R.id.btnCommentReplay);
            imgVector = itemView.findViewById(R.id.imgCommentVector);
        }
    }

    @SuppressLint("MissingInflatedId")
    private void loadReplay(int id ,String nickName, String userName) {
        View view = activity.getLayoutInflater().inflate(R.layout.sheet_replay, null);
        BottomSheetDialog replaySheet = new BottomSheetDialog(activity);
        replaySheet.setContentView(view);
        replaySheet.show();
        TextView titleReplay, userReplay;
        RecyclerView recyclerViewReplay;

        titleReplay = view.findViewById(R.id.repNickname);
        userReplay = view.findViewById(R.id.repUsername);
        recyclerViewReplay = view.findViewById(R.id.recyclerViewReplay);

        titleReplay.setText(nickName);
        userReplay.setText(userName);

        requestReplay = ApiClinent.getApiClinent(activity, Key.BASE_URL).create(ApiInterFace.class);
        recyclerViewReplay.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(new FragmentActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerViewReplay.setLayoutManager(layoutManager);
        requestReplay.getReplay(id).enqueue(new Callback<List<Replay>>() {
            @Override
            public void onResponse(Call<List<Replay>> call, Response<List<Replay>> response) {
                listReplay = response.body();
                replayAdapter = new ReplayAdapter(activity.getApplicationContext(), listReplay, activity);
                recyclerViewReplay.setAdapter(replayAdapter);
            }

            @Override
            public void onFailure(Call<List<Replay>> call, Throwable t) {
                SnackBoard.show(activity,"خطای سمت سرور", 0);
            }
        });

    }


}
