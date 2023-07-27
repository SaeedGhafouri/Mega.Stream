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

    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
    static Snackbar Message(FragmentActivity activity, String messgae, String option){
        final Snackbar snackbar = Snackbar.make(activity.findViewById(R.id.content), "", Snackbar.LENGTH_SHORT);
        View view = activity.getLayoutInflater().inflate(R.layout.snack_message, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(0,0,0,0);
        snackbarLayout.addView(view,0);
        snackbar.show();

        TextView txtMsg;
        ImageView icoMsg;
        LinearLayout bgMsg;
        txtMsg = view.findViewById(R.id.snackText);
        txtMsg.setText(messgae);
        icoMsg = view.findViewById(R.id.snackIcon);
        bgMsg = view.findViewById(R.id.snackBg);

        if (option.equals("SUCCESS")) {
            bgMsg.setBackgroundResource(R.color.theme_success);
            icoMsg.setImageResource(R.drawable.ic_close);
        }else if (option.equals("WARNING")){
            bgMsg.setBackgroundResource(R.color.theme_warning);
            icoMsg.setImageResource(R.drawable.ic_close);
        } else if (option.equals("ERROR")) {
            bgMsg.setBackgroundResource(R.color.theme_danger);
            icoMsg.setImageResource(R.drawable.ic_close);
        }

        return snackbar;
    }

}
