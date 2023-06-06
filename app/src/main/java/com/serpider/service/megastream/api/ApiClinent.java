package com.serpider.service.megastream.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClinent {

    public static Retrofit retrofit = null;

    public static Retrofit getApiClinent(String url) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
