package com.serpider.service.megastream.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatToggleButton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.serpider.service.megastream.R;

public class PlayerActivity extends AppCompatActivity {

    private VideoView videoView;
    private TextView titleVideo, txtTimeRun, txtTimeAll;
    private AppCompatToggleButton btnPlay;
    private boolean running;

    @SuppressLint("AppCompatMethod")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();

        loadData();
        playerControler();

    }

    private void playerControler() {

        /*Button Play Pause*/
        btnPlay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isPlay) {
                if (isPlay){
                    videoView.start();
                }else {
                    videoView.pause();
                }
            }
        });

        /*Button Text Run*/
        videoView.setOnPreparedListener(mediaPlayer -> {
            running = true;
            final int duration = videoView.getDuration();
            new Thread(new Runnable() {
                public void run() {
                    do{
                        txtTimeRun.post(new Runnable() {
                            public void run() {
                                int time = (duration - videoView.getCurrentPosition())/1000;
                                txtTimeRun.setText(time+"");
                            }
                        });
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(!running) break;
                    }
                    while(videoView.getCurrentPosition()<duration);
                }
            }).start();


        });

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
    }

    private void loadData() {

        Bundle extras = getIntent().getExtras();
        String urlPlay = extras.getString("URL_PLAY");
        String titlePlay = extras.getString("URL_TITLE");

        titleVideo.setText(titlePlay);
        videoView.setVideoPath(urlPlay);
        videoView.start();

    }

    public void init() {
        videoView = findViewById(R.id.videoPlayer);
        titleVideo = findViewById(R.id.txtNameFilm);
        btnPlay = findViewById(R.id.btnPlayPlayer);
        txtTimeRun = findViewById(R.id.txtTimeRun);
    }
}