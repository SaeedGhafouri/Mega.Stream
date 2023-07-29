package com.serpider.service.megastream.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.serpider.service.megastream.R;

public class SnackBoard {

    public static void show(Context context, String message, int type) {
        View layout = LayoutInflater.from(context).inflate(R.layout.snack_message, null);
        ImageView imageViewIcon = layout.findViewById(R.id.snackIcon);

        LinearLayout bgSnack = layout.findViewById(R.id.snackBg);

        TextView textViewMessage = layout.findViewById(R.id.snackText);
        textViewMessage.setText(message);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);

        if (type == 0){
            imageViewIcon.setImageResource(R.drawable.ic_close);
            bgSnack.setBackgroundColor(context.getResources().getColor(R.color.theme_danger));
        } else if (type == 1) {
            imageViewIcon.setImageResource(R.drawable.ic_close);
            bgSnack.setBackgroundColor(context.getResources().getColor(R.color.theme_success));
        } else if (type == 2) {
            imageViewIcon.setImageResource(R.drawable.ic_close);
            bgSnack.setBackgroundColor(context.getResources().getColor(R.color.theme_warning));
        }else if (type == 3) {
            imageViewIcon.setImageResource(R.drawable.ic_close);
            bgSnack.setBackgroundColor(context.getResources().getColor(R.color.theme_info));
        }

        toast.show();
    }
}
