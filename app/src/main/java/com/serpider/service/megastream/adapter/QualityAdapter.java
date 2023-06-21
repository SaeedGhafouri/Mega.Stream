package com.serpider.service.megastream.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.model.Movie;
import com.serpider.service.megastream.ui.MainActivity;
import com.serpider.service.megastream.ui.PlayerActivity;

import java.util.List;

public class QualityAdapter extends RecyclerView.Adapter<QualityAdapter.MyViewHolder> {

    Context context;
    List<Movie> data;
    FragmentActivity fragmentActivity;

    public QualityAdapter(Context context, List<Movie> data, FragmentActivity fragmentActivity) {
        this.context = context;
        this.data = data;
        this.fragmentActivity = fragmentActivity;
    }

    @NonNull
    @Override
    public QualityAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_quality, parent, false);
        return new QualityAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QualityAdapter.MyViewHolder holder, int position) {

        holder.txtTitle.setText(data.get(position).getMovie_quality());
        holder.txtSize.setText(data.get(position).getMovie_size());
        holder.txtLangueg.setText(data.get(position).getMovie_language());

        holder.itemView.setOnClickListener(view -> {
            playerSheet(fragmentActivity, data.get(position).getMovie_title(), data.get(position).getMovie_play_url());
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgQuality;
        TextView txtTitle, txtSize, txtLangueg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgQuality = itemView.findViewById(R.id.qualityImg);
            txtTitle = itemView.findViewById(R.id.qualityTitle);
            txtSize = itemView.findViewById(R.id.qualitySize);
            txtLangueg = itemView.findViewById(R.id.qualityType);
        }
    }

    @SuppressLint("MissingInflatedId")
    static void playerSheet(FragmentActivity fragmentActivity, String title, String url) {
        View view = fragmentActivity.getLayoutInflater().inflate(R.layout.sheet_player, null);
        BottomSheetDialog PlayerSheet = new BottomSheetDialog(fragmentActivity);
        PlayerSheet.setContentView(view);
        PlayerSheet.show();

        LinearLayout btnVlc, btnMga;
        btnVlc = view.findViewById(R.id.btnVlcPlayer);
        btnMga = view.findViewById(R.id.btnPlayerMga);

        /*Mega Player*/
        btnMga.setOnClickListener(view1 -> {
            Intent intent = new Intent(fragmentActivity, PlayerActivity.class);
            intent.putExtra("URL_PLAY", url);
            intent.putExtra("URL_TITLE", title);
            fragmentActivity.startActivity(intent);
        });

        /*VLC Player*/
        int vlcRequestCode = 42;
        btnVlc.setOnClickListener(view1 -> {
            Uri uri = Uri.parse(url);
            Intent vlcIntent = new Intent(Intent.ACTION_VIEW);
            vlcIntent.setPackage("org.videolan.vlc");
            vlcIntent.setDataAndTypeAndNormalize(uri, "video/*");
            vlcIntent.putExtra("title", title);
            vlcIntent.putExtra("from_start", true);
            vlcIntent.putExtra("position", 90000l);
            fragmentActivity.startActivityForResult(vlcIntent, vlcRequestCode);
        });

    }

}
