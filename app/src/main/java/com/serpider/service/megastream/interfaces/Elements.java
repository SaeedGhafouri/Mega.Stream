package com.serpider.service.megastream.interfaces;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import androidx.fragment.app.FragmentActivity;

import com.serpider.service.megastream.R;

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

}
