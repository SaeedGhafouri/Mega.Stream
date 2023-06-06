package com.serpider.service.megastream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.serpider.service.megastream.R;
import com.serpider.service.megastream.model.Country;
import com.serpider.service.megastream.model.Slider;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private View view;
    private ScaleGestureDetector mScaleGestureDetector;
    public ImageView imageView;
    public TextView titleSlider, descSlider;

    List<Slider> data;

    private float mScaleFactor = 1.0f;

    public SliderAdapter(Context context, LayoutInflater layoutInflater, List<Slider> data) {
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull View container,final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.item_slider, null);
        imageView = (ImageView) view.findViewById(R.id.sliderImg);
        titleSlider = view.findViewById(R.id.titleSlider);
        descSlider = view.findViewById(R.id.descSlider);
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
        // imageView.setImageResource();
        Picasso.get().load(data.get(position).getSlider_image()).into(imageView);
        titleSlider.setText(data.get(position).getSlider_title());
        descSlider.setText(data.get(position).getSlider_desc());

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        // when a scale gesture is detected, use it to resize the image
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            view.setScaleX(mScaleFactor);
            view.setScaleY(mScaleFactor);
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));
            return true;
        }
    }


}
