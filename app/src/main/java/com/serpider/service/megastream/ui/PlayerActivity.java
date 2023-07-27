package com.serpider.service.megastream.ui;

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

import com.serpider.service.megastream.R;

import java.util.Locale;

public class PlayerActivity extends AppCompatActivity {

    private VideoView videoView;
    private ToggleButton toggleButtonPlayPause;
    private SeekBar seekBar, seekBarVolume, seekBarBrightness;
    private TextView textViewTotalDuration, textViewCurrentDuration;
    private AudioManager audioManager;
    private int maxVolume;

    @SuppressLint("AppCompatMethod")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
        loadData();
        controler();

    }
    public void init() {
        videoView = findViewById(R.id.videoPlayer);
        seekBar = findViewById(R.id.seekBar);
        textViewTotalDuration = findViewById(R.id.textViewTotalDuration);
        textViewCurrentDuration = findViewById(R.id.textViewCurrentDuration);
        toggleButtonPlayPause = findViewById(R.id.toggleButtonPlayPause);
        seekBarVolume = findViewById(R.id.seekBarVolume);
        seekBarBrightness = findViewById(R.id.seekBarBrightness);
    }

    private void loadData() {
        Bundle extras = getIntent().getExtras();
        String urlPlay = extras.getString("URL_PLAY");
        String titlePlay = extras.getString("URL_TITLE");

        Uri videoUri = Uri.parse(urlPlay);
        videoView.setVideoURI(videoUri);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                int duration = videoView.getDuration();
                int currentPosition = videoView.getCurrentPosition();

                seekBar.setMax(100);
                seekBar.setProgress(0);

                seekBar.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int currentPosition = videoView.getCurrentPosition();
                        int totalDuration = videoView.getDuration();
                        int progress = (int) ((currentPosition * 100L) / totalDuration);
                        seekBar.setProgress(progress);
                        textViewCurrentDuration.setText(formatTime(currentPosition));
                        textViewTotalDuration.setText("Total Duration: " + formatTime(totalDuration));

                        seekBar.postDelayed(this, 1000);
                    }
                }, 1000);
            }
        });
    }

    private String formatTime(int milliseconds) {
        int seconds = (milliseconds / 1000) % 60;
        int minutes = (milliseconds / (1000 * 60)) % 60;
        int hours = (milliseconds / (1000 * 60 * 60)) % 24;

        String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        return time;
    }

    public void controler(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    int duration = videoView.getDuration();
                    int newPosition = duration * progress / 100;
                    videoView.seekTo(newPosition);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // اجرای دلخواه در زمان شروع جابه‌جایی
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // اجرای دلخواه در زمان پایان جابه‌جایی
            }
        });

        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volume = (float) progress / 100;
                int currentVolume = (int) (maxVolume * volume);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // اجرای دلخواه در زمان شروع جابه‌جایی
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // اجرای دلخواه در زمان پایان جابه‌جایی
            }
        });

        seekBarBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float brightness = progress / 100f;
                WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                layoutParams.screenBrightness = brightness;
                getWindow().setAttributes(layoutParams);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // اجرای دلخواه در زمان شروع جابه‌جایی
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // اجرای دلخواه در زمان پایان جابه‌جایی
            }
        });
        
        toggleButtonPlayPause.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    videoView.pause();
                } else {
                    videoView.start();
                }
            }
        });
    }

}