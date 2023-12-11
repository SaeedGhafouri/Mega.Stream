package com.serpider.service.megastream.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.model.Download;
import java.util.List;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.MyViewHolder> {

    private FragmentActivity activity;
    private List<Download> data;

    public DownloadAdapter(FragmentActivity activity, List<Download> data) {
        this.activity = activity;
        this.data = data;
    }

    @NonNull
    @Override
    public DownloadAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_download, parent, false);
        return new DownloadAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Download download = data.get(position);
        holder.txtTitle.setText(download.getTitle());
        holder.txtYear.setText(download.getYear());
        holder.txtTime.setText(download.getTimer());
        holder.progressBar.setProgress(download.getProgress());

        Glide.with(activity).load(download.getPoster()).into(holder.poster);

        holder.itemView.setOnClickListener(view -> showPopUp(holder.btnMore, download));
    }

    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtYear, txtTime;
        private ImageView poster;
        private ProgressBar progressBar;
        private ImageView btnMore;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtDmTitle);
            txtYear = itemView.findViewById(R.id.txtDmYear);
            txtTime = itemView.findViewById(R.id.txtDmTimer);
            poster = itemView.findViewById(R.id.imgDmPoster);
            progressBar = itemView.findViewById(R.id.pDmProgress);
            btnMore = itemView.findViewById(R.id.btnDmMore);
        }
    }

    private void showPopUp(View anchor, Download download) {
        PopupMenu popupMenu = new PopupMenu(activity, anchor);
        popupMenu.getMenuInflater().inflate(R.menu.download_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.btnResume:
                    // ادامه دانلود
                    resumeDownload(download);
                    return true;
                case R.id.btnStop:
                    // توقف دانلود
                    stopDownload(download);
                    return true;
                case R.id.btnPath:
                    // نمایش محل دانلود
                    showDownloadPath(download);
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.show();
    }

    private void resumeDownload(Download download) {
        DownloadManager downloadManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(download.getLink()))
                .setTitle(download.getTitle())
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, download.getTitle() + ".mp4");

        int downloadId = (int) downloadManager.enqueue(request);

        download.setId(downloadId);

        int newProgressValue = download.getProgress() + 10;
        download.setProgress(newProgressValue);

        // به‌روزرسانی RecyclerView
        updateData(data);
    }

    private void stopDownload(Download download) {
        // برای توقف دانلود، از DownloadManager استفاده کنید.
        DownloadManager downloadManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.remove(download.getId());

        // تنظیم پیشرفت به 0
        download.setProgress(0);

        // به‌روزرسانی RecyclerView
        updateData(data);
    }

    private void showDownloadPath(Download download) {
        // برای نمایش محل دانلود، از محل ذخیره فایل‌ها استفاده کنید.
        // مثلا:
        String downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + download.getTitle() + ".mp4";

        // حالا می‌توانید از downloadPath برای نمایش محل ذخیره شده استفاده کنید.
        showToast("Download Path: " + downloadPath);
    }

    private void showToast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public void updateData(List<Download> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }
}
