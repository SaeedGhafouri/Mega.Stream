package com.serpider.service.megastream.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.serpider.service.megastream.R;

public class Toaster {

    public static void error(Context context, String message, int duration) {
        showToast(context, message, R.drawable.ic_close,R.color.theme_danger, duration);
    }

    public static void success(Context context, String message, int duration) {
        showToast(context, message, R.drawable.ic_check_mark, R.color.theme_success, duration);
    }

    public static void warning(Context context, String message, int duration) {
        showToast(context, message, R.drawable.ic_check_mark, R.color.theme_warning, duration);
    }

    public static void info(Context context, String message, int duration) {
        showToast(context, message, R.drawable.ic_check_mark, R.color.theme_info, duration);
    }

    @SuppressLint("MissingInflatedId")
    private static void showToast(Context context, String message, int icon, int bgColor, int duration) {
        // Inflate the custom layout for the toast
        View layout = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);

        // Customize the layout
        LinearLayout toastLayout = layout.findViewById(R.id.toastBg);
        toastLayout.setBackgroundColor(ContextCompat.getColor(context, bgColor));

        ImageView imageViewIcon = layout.findViewById(R.id.toastIcon);
        imageViewIcon.setImageResource(icon);

        TextView textViewMessage = layout.findViewById(R.id.toastText);
        textViewMessage.setText(message);

        // Create and show the toast
        Toast toast = new Toast(context);
        toast.setMargin(0, 0.1f);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.setDuration(duration);
        toast.setView(layout);
        toast.show();
    }
}
