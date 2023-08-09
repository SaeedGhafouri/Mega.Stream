package com.serpider.service.megastream.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatToggleButton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.VideoView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.serpider.service.megastream.R;

import java.util.Locale;

public class PlayerActivity extends AppCompatActivity {

    YouTubePlayerView playerView;


    @SuppressLint("AppCompatMethod")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        init();

        getLifecycle().addObserver(playerView);

        playerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "https://dl2.freeverss.online/film2/1402/05/Joy.Ride.2023/Joy.Ride.2023.480p.WEB-DL.x264.Pahe.SoftSub.AvaMovie.mkv";
                youTubePlayer.loadVideo(videoId, 0);
            }
        });



    }
    public void init() {
        playerView = findViewById(R.id.youtube_player_view);
    }

    private void loadData() {
        Bundle extras = getIntent().getExtras();
        String urlPlay = extras.getString("URL_PLAY");
        String titlePlay = extras.getString("URL_TITLE");

        Uri videoUri = Uri.parse(urlPlay);

    }

}