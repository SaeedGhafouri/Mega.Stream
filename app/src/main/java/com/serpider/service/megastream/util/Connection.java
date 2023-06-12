package com.serpider.service.megastream.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.FragmentActivity;

import com.serpider.service.megastream.R;

public class Connection {

    public boolean isNetwork(FragmentActivity fragmentActivity) {
        boolean isConnected = false;
        ConnectivityManager connectivityManager = ((ConnectivityManager) fragmentActivity.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected())
            isConnected = true;
        return isConnected;
    }

    public Dialog showDialog(FragmentActivity fragmentActivity) {
        final Dialog dialog = new Dialog(fragmentActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_connection);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (isNetwork(fragmentActivity)) {
            dialog.show();
        }else {
            dialog.dismiss();
        }

        return dialog;
    }

}
