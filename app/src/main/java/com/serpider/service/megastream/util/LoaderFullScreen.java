package com.serpider.service.megastream.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

import com.serpider.service.megastream.R;

public class LoaderFullScreen {
    private Dialog dialog;
    private Context context;

    private static LoaderFullScreen instance;

    private LoaderFullScreen(Context context) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_loader_full_screen);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(layoutParams);
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        dialog.show();
        dialog.getWindow().getAttributes();
    }

    public void show() {
        dialog.show();
    }

    public void close() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static LoaderFullScreen getInstance(Context context) {
        if (instance == null) {
            instance = new LoaderFullScreen(context);
        }
        return instance;
    }
}