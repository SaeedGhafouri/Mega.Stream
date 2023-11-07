package com.serpider.service.megastream.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.model.Movie_Play;
import com.serpider.service.megastream.ui.activity.PlayerActivity;

import java.util.List;

public class QualityAdapter extends RecyclerView.Adapter<QualityAdapter.MyViewHolder> {

    Context context;
    List<Movie_Play> data;
    FragmentActivity activity;

    public QualityAdapter(Context context, List<Movie_Play> data, FragmentActivity activity) {
        this.context = context;
        this.data = data;
        this.activity = activity;
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

        if (data.get(position).getMovie_quality().equals("2160p")) {
            holder.imgQuality.setImageResource(R.drawable.ic_2160);
        }else if (data.get(position).getMovie_quality().equals("1080p")) {
            holder.imgQuality.setImageResource(R.drawable.ic_1080);
        } else if (data.get(position).getMovie_quality().equals("720p")) {
            holder.imgQuality.setImageResource(R.drawable.ic_720);
        } else if (data.get(position).getMovie_quality().equals("720p ×265")) {
            holder.imgQuality.setImageResource(R.drawable.ic_720);
        }else if (data.get(position).getMovie_quality().equals("480p")) {
            holder.imgQuality.setImageResource(R.drawable.ic_480);
        }else if (data.get(position).getMovie_quality().equals("360p")) {
            holder.imgQuality.setImageResource(R.drawable.ic_360);
        }

        holder.itemView.setOnClickListener(view -> {
            playerSheet(activity, data.get(position).getMovie_title(), data.get(position).getMovie_play_url());
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
    static void playerSheet(FragmentActivity activity, String title, String url) {
        View view = activity.getLayoutInflater().inflate(R.layout.sheet_player, null);
        BottomSheetDialog PlayerSheet = new BottomSheetDialog(activity);
        PlayerSheet.setContentView(view);
        PlayerSheet.show();

        LinearLayout btnVlc, btnMga, btnMx;
        TextView txtVlc, txtMx, txtKm;
        btnVlc = view.findViewById(R.id.btnVlcPlayer);
        btnMga = view.findViewById(R.id.btnPlayerMga);
        btnMx = view.findViewById(R.id.btnMxPlayer);
        txtVlc = view.findViewById(R.id.txtVlcInstall);
        txtMx = view.findViewById(R.id.txtMxInstall);
        txtKm = view.findViewById(R.id.txtKmInstall);

        /*Mega Player*/
        btnMga.setOnClickListener(view1 -> {
            Intent intent = new Intent(activity, PlayerActivity.class);
            intent.putExtra("URL_PLAY", url);
            intent.putExtra("URL_TITLE", title);
            activity.startActivity(intent);
        });

        /*VLC Player*/
        if (isAppAvailable(activity, "org.videolan.vlc")) {
            btnVlc.setEnabled(true);
            txtVlc.setText("Installed");
        } else {
            txtVlc.setText("not installed");
        }
        btnVlc.setOnClickListener(view1 -> {
            getIntentPlayer(activity, url, title, "org.videolan.vlc");
        });

        /*Mx Player*/
        btnMx.setOnClickListener(view1 -> {

        });

    }
    
    static void getIntentPlayer(FragmentActivity activity,String url, String title, String packageName) {
        Uri uri = Uri.parse(url);
        Intent Intent = new Intent(android.content.Intent.ACTION_VIEW);
        Intent.setPackage(packageName);
        Intent.setDataAndTypeAndNormalize(uri, "video/*");
        Intent.putExtra("title", title);
        Intent.putExtra("from_start", true);
        Intent.putExtra("position", 90000l);
        activity.startActivityForResult(Intent,  42);
    }

    public static boolean isAppAvailable(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("INSTALL1", e.getMessage());
            return false;

        }
    }

}
