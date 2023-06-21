package com.serpider.service.megastream.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.model.Comment;
import com.serpider.service.megastream.model.Network;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    Context context;
    List<Comment> data;
    FragmentActivity activity;

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
        holder.txtNick.setText(data.get(position).getComment_user_nick());
        holder.txtUsername.setText("@" + data.get(position).getComment_user_name());
        holder.txtMsg.setText(data.get(position).getComment_msg());
        holder.txtDate.setText(data.get(position).getComment_date());

        holder.btnReply.setOnClickListener(view -> loadReplay(data.get(position).getComment_user_nick(), data.get(position).getComment_user_name()));

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
    private void loadReplay(String nickName, String userName) {
        View view = activity.getLayoutInflater().inflate(R.layout.sheet_replay, null);
        BottomSheetDialog replaySheet = new BottomSheetDialog(activity);
        replaySheet.setContentView(view);
        replaySheet.show();
        TextView titleReplay, userReplay;
        RecyclerView recyclerView;

        titleReplay = view.findViewById(R.id.repNickname);
        userReplay = view.findViewById(R.id.repUsername);

        titleReplay.setText(nickName);
        userReplay.setText(userName);


    }


}
