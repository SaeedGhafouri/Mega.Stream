package com.serpider.service.megastream.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.serpider.service.megastream.R;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.interfaces.Key;
import com.serpider.service.megastream.model.Settings;
import com.serpider.service.megastream.util.DataSave;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ApiInterFace request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appSettings();
    }

    private void appSettings() {
        request = ApiClinent.getApiClinent(this, Key.BASE_URL).create(ApiInterFace.class);
        request.getSetting().enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                Settings settings = response.body();
                if (settings.isPower()) {
                    DataSave.settingPerfence(getApplicationContext(),
                            settings.isPower(),
                            settings.getSup_phone(),
                            settings.getSup_email(),
                            settings.getSup_telegram(),
                            settings.getSup_insta(),
                            settings.getSup_x(),
                            settings.getCount_item());
                }else {
                    Toast.makeText(MainActivity.this, "Power: False", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Settings> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}