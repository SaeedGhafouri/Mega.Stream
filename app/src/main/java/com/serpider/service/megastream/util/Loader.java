package com.serpider.service.megastream.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.serpider.service.megastream.R;

public class Loader {
    private Dialog dialog;
    private Context context;

    private static Loader instance;

    private Loader(Context context) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_loader);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

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

    public static Loader getInstance(Context context) {
        if (instance == null) {
            instance = new Loader(context);
        }
        return instance;
    }
}