package com.serpider.service.megastream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.serpider.service.megastream.R;

public class SpinnerAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private String[] mData;
    private int[] mIcons;

    private long[] mPrices;

    public SpinnerAdapter(@NonNull Context context, String[] data, int[] icons, long[] price) {
        super(context, R.layout.item_donate, data);
        mContext = context;
        mData = data;
        mIcons = icons;
        mPrices = price;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    private View createView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_donate, parent, false);

        ImageView iconImageView = view.findViewById(R.id.item_donate_icon);
        TextView textTextView = view.findViewById(R.id.item_donate_text);

        iconImageView.setImageResource(mIcons[position]);
        textTextView.setText(mData[position]);

        return view;
    }
}
