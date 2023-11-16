package com.serpider.service.megastream.api;

import static com.serpider.service.megastream.interfaces.Elements.Version;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.core.os.BuildCompat;

import com.serpider.service.megastream.BuildConfig;

public interface ApiServer {

    /*Url Source Data*/
    /*static String urlData() {
        String Url = "https://serpider.com/Mega/Service-App/Api_Source/";
        return Url;
    }*/
    /*static String urlData() {
        String Url = "http://moblnodezh.com/Mega-Test/Api-Service/";
        return Url;
    }*/

    static String URL() {
        String Url = "http://pluslux.xyz/Mega/" + Version() + "/api.php?action=";
        return Url;
    }

}
