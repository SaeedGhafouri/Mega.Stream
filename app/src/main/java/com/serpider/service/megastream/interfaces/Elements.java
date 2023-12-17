package com.serpider.service.megastream.interfaces;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.animation.content.Content;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.serpider.service.megastream.BuildConfig;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.util.SnackBoard;


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

        ImageButton btnClose = dialog.findViewById(R.id.btnClose);
        LottieAnimationView loader = dialog.findViewById(R.id.loader);
        VideoView videoView = dialog.findViewById(R.id.dialogVideoPlayer);
        Uri uri = Uri.parse(urlVideo);
        videoView.setVideoURI(uri);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.start();
                loader.setVisibility(View.GONE);
                loader.pauseAnimation();
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                dialog.dismiss();
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                dialog.dismiss();
                SnackBoard.show(fragment, "خطا در پخش تریلر", 0);
                return false;
            }
        });


        btnClose.setOnClickListener(view -> dialog.dismiss());

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
        Glide.with(activity).load(urlImage).into(img);


        return dialog;
    }

    /*Subscription*/
    static void sheetSubscription(FragmentActivity activity, int action) {
        View view = activity.getLayoutInflater().inflate(R.layout.sheet_subscription, null);
        BottomSheetDialog sheet = new BottomSheetDialog(activity);
        sheet.setContentView(view);
        sheet.show();
        MaterialButton btnActive = view.findViewById(R.id.btnActive);
        btnActive.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(action);
            sheet.dismiss();
        });
    }

    /*chr veriosn bridge in api*/
    static String Version(){
        String versionName = BuildConfig.VERSION_NAME;
        int versionCode = BuildConfig.VERSION_CODE;
        return versionCode+versionName.replace(".", "");
    }

    static String UserIdConvers(int id) {
        String result = "";
        if (id == 0) {
            int c = id + 150;
        }
        return result;

    }

}
