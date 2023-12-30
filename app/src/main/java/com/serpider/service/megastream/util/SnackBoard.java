package com.serpider.service.megastream.util;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import com.google.android.material.snackbar.Snackbar;
import com.serpider.service.megastream.R;

public class SnackBoard {

    public static void show(FragmentActivity activity, String message, int type) {

        View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);

        final Snackbar snackbar = Snackbar.make(rootView, "", Snackbar.LENGTH_LONG);

        View snackView = activity.getLayoutInflater().inflate(R.layout.snack_message, null);

        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(20, 20, 20, 20);
        snackbarLayout.addView(snackView, 0);

        ImageView imageViewIcon = snackView.findViewById(R.id.snackIcon);
        LinearLayout bgSnack = snackView.findViewById(R.id.snackBg);
        TextView textViewMessage = snackView.findViewById(R.id.snackText);
        textViewMessage.setText(message);

        if (type == 0) {
            imageViewIcon.setImageResource(R.drawable.ic_close);
            bgSnack.setBackgroundColor(activity.getResources().getColor(R.color.theme_danger));
        } else if (type == 1) {
            imageViewIcon.setImageResource(R.drawable.ic_close);
            bgSnack.setBackgroundColor(activity.getResources().getColor(R.color.theme_success));
        } else if (type == 2) {
            imageViewIcon.setImageResource(R.drawable.ic_close);
            bgSnack.setBackgroundColor(activity.getResources().getColor(R.color.theme_warning));
        } else if (type == 3) {
            imageViewIcon.setImageResource(R.drawable.ic_close);
            bgSnack.setBackgroundColor(activity.getResources().getColor(R.color.theme_info));
        }
        snackbar.show();
    }
}

