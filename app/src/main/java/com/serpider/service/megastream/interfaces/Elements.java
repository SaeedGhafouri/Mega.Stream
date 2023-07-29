package com.serpider.service.megastream.interfaces;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.fragment.app.FragmentActivity;

import com.airbnb.lottie.animation.content.Content;
import com.google.android.material.snackbar.Snackbar;
import com.serpider.service.megastream.R;
import com.squareup.picasso.Picasso;

public interface Elements {

    /*Triler Diloag*/
    static Dialog DialogVideoPlayer(FragmentActivity fragment, String urlVideo){
        final Dialog dialog = new Dialog(fragment);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_player);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        VideoView videoView = dialog.findViewById(R.id.dialogVideoPlayer);
        Uri uri = Uri.parse(urlVideo);
        videoView.setVideoURI(uri);
        videoView.start();

        return dialog;
    }

    static Dialog DialogPreImage(FragmentActivity activity, String urlImage) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_preview_image);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        ImageView img = dialog.findViewById(R.id.previewImage);
        Picasso.get().load(urlImage).into(img);

        return dialog;
    }


}
