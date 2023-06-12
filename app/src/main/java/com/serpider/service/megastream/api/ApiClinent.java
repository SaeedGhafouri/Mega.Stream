package com.serpider.service.megastream.api;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.fragment.app.FragmentActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.serpider.service.megastream.util.Connection;

import java.io.IOException;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClinent {

    public static Retrofit retrofit = null;

    public static Retrofit getApiClinent(FragmentActivity activity, String url) {

        long cacheSize = (5 * 1024 * 1024);
        Cache myCache = new Cache(activity.getCacheDir(), cacheSize);
        Connection connection = new Connection();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(myCache)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();

                        if (connection.isNetwork(activity)){
                            request = request.newBuilder().addHeader("Cache-Control", "public, max-age=" + 5).build();
                        }else{
                            request = request.newBuilder().addHeader(
                                    "Cache-Control",
                                    "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                            ).build();
                        }
                        return chain.proceed(request);
                    }
                }).build();

        if (retrofit==null) {

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

}
