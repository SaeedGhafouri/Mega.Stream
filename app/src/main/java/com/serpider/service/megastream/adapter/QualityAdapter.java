package com.serpider.service.megastream.adapter;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
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
import com.serpider.service.megastream.model.PlayUrl;
import com.serpider.service.megastream.ui.activity.PlayerActivity;

import java.util.List;

public class QualityAdapter extends RecyclerView.Adapter<QualityAdapter.MyViewHolder> {

    Context context;
    List<PlayUrl> data;
    private FragmentActivity activity;
    private String title;

    public QualityAdapter(Context context, List<PlayUrl> data, FragmentActivity activity, String title) {
        this.context = context;
        this.data = data;
        this.activity = activity;
        this.title = title;
    }

    @NonNull
    @Override
    public QualityAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_quality, parent, false);
        return new QualityAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QualityAdapter.MyViewHolder holder, int position) {

        holder.txtTitle.setText(String.valueOf(data.get(position).getQuality()));
        holder.txtLangueg.setText(data.get(position).getLanguage());
        holder.txtSize.setText(data.get(position).getSize() + " MB");

        if (data.get(position).getQuality() == 2160) {
            holder.imgQuality.setImageResource(R.drawable.ic_2160);
        }else if (data.get(position).getQuality() == 1080) {
            holder.imgQuality.setImageResource(R.drawable.ic_1080);
        } else if (data.get(position).getQuality() == 720) {
            holder.imgQuality.setImageResource(R.drawable.ic_720);
        } else if (data.get(position).getQuality() == 722) {
            holder.imgQuality.setImageResource(R.drawable.ic_720);
        }else if (data.get(position).getQuality() == 480) {
            holder.imgQuality.setImageResource(R.drawable.ic_480);
        }else if (data.get(position).getQuality() == 360) {
            holder.imgQuality.setImageResource(R.drawable.ic_360);
        }
        holder.itemView.setOnClickListener(view -> {
            playerSheet(title, data.get(position).getPlay());
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
    void playerSheet(String title, String url) {
        View view = activity.getLayoutInflater().inflate(R.layout.sheet_player, null);
        BottomSheetDialog PlayerSheet = new BottomSheetDialog(activity);
        PlayerSheet.setContentView(view);
        PlayerSheet.show();
        PackageManager packageManager = activity.getPackageManager();

        LinearLayout btnVlc, btnMga, btnMx, btnKm;
        TextView txtVlc, txtMx, txtKm;
        btnVlc = view.findViewById(R.id.btnVlcPlayer);
        btnKm = view.findViewById(R.id.btnKmPlayer);
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


        /* Vlc Player */
        if (isPackageInstalled("org.videolan.vlc", packageManager)) {
            txtVlc.setText("Installed");
        } else {
            txtVlc.setText("Not installed");
            btnVlc.setEnabled(false);
        }

        btnVlc.setOnClickListener(view1 -> {
            //getIntentPlayer(activity, url, title, "com.serpider.service.megastream");
            intentVlcPlayer(url, title);
        });

        /* Km Player */
        if (isPackageInstalled("com.kmplayer", packageManager)) {
            txtKm.setText("Installed");
        } else {
            txtKm.setText("Not installed");
            btnKm.setEnabled(false);
        }

        btnKm.setOnClickListener(view1 -> {
            getIntentPlayer(activity, url, title, "com.kmplayer");
        });

        /* Mx Player */
        if (isPackageInstalled("com.mxtech.videoplayer.ad", packageManager)) {
            txtMx.setText("Installed");
        } else {
            txtMx.setText("Not installed");
            btnMx.setEnabled(false);
        }

        btnMx.setOnClickListener(view1 -> {
            intentMxPlayer(url, title);
        });


    }

    static void getIntentPlayer(FragmentActivity activity, String url, String title, String packageName) {
        Uri uri = Uri.parse(url);
        Intent playerIntent = new Intent(android.content.Intent.ACTION_VIEW);
        playerIntent.setDataAndTypeAndNormalize(uri, "video/*");
        playerIntent.putExtra("title", title);
        playerIntent.putExtra("from_start", true);
        playerIntent.putExtra("position", 90000L);

        if ("com.kmplayer".equals(packageName) && isPackageInstalled(packageName, activity.getPackageManager())) {
            playerIntent.setPackage(packageName);
        } else if ("com.mxtech.videoplayer.ad".equals(packageName) && isPackageInstalled(packageName, activity.getPackageManager())) {
            playerIntent.setPackage(packageName);
        } else if ("com.serpider.service.megastream".equals(packageName) && isPackageInstalled(packageName, activity.getPackageManager())) {
            playerIntent.setPackage(packageName);
        } else {
            Toast.makeText(activity, "برنامه مرتبط باز نشد. لطفاً نصب کنید.", Toast.LENGTH_SHORT).show();
            return;
        }

        // با استفاده از startActivityForResult می‌توانید اطلاعات بازگشتی را دریافت کنید
        activity.startActivityForResult(playerIntent, 42);
    }

    private void intentVlcPlayer(String url,String title) {
        int vlcRequestCode = 42;
        Uri uri = Uri.parse(url);
        Intent vlcIntent = new Intent(Intent.ACTION_VIEW);
        vlcIntent.setPackage("org.videolan.vlc");
        vlcIntent.setDataAndTypeAndNormalize(uri, "video/*");
        vlcIntent.putExtra("title", title);
        vlcIntent.putExtra("from_start", false);
        vlcIntent.putExtra("position", 90000l);
      //  vlcIntent.putExtra("subtitles_location", "/sdcard/Movies/Fifty-Fifty.srt");
        activity.startActivityForResult(vlcIntent, vlcRequestCode);
    }
    private void intentMxPlayer(String url,String title) {
        Intent intent;
        try {
            intent= new Intent(Intent.ACTION_VIEW);
            intent.setClassName(context,"com.mxtech.videoplayer.pro");
            if (null != intent)
                intent.setDataAndType(Uri.parse(url), "video/*");
            activity.startActivity(intent);
        }
        catch (ActivityNotFoundException e) {
            try{
                intent= new Intent(Intent.ACTION_VIEW);
                intent.setClassName(context,"com.mxtech.videoplayer.ad");
                if (null != intent)
                    intent.setDataAndType(Uri.parse(url), "video/*");
                activity.startActivity(intent);
            }
            catch (ActivityNotFoundException er) {

            }
        }
    }

    private void intentKmPlayer() {

    }

    private static boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
