package com.serpider.service.megastream.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.ui.fragment.WebFragment;
import com.serpider.service.megastream.model.Ads;


import java.util.List;

public class SliderAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private View view;
    private ScaleGestureDetector mScaleGestureDetector;
    public ImageView imageView;
    private FragmentActivity fragmentActivity;
    private WebFragment webFragment;
    List<Ads> data;

    private float mScaleFactor = 1.0f;

    public SliderAdapter(Context context, LayoutInflater layoutInflater, List<Ads> data, FragmentActivity fragmentActivity) {
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.data = data;
        this.fragmentActivity = fragmentActivity;
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
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
        // imageView.setImageResource();
        Glide.with(context).load(data.get(position).getBanner()).into(imageView);

        /*Check Mode*/
        int MODE = data.get(position).getType();

        String LINK = data.get(position).getLink();
        changeMode(MODE,LINK);

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    private void changeMode(int mode, String url) {
        /*
        1 = Donate
        2 = Ads
        3 = Web
        4 = Watch
        */
        if (mode == 1) {
            imageView.setOnClickListener(view1 -> Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_donateFragment));
        } else if (mode == 2) {
            imageView.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.action_mainFragment_to_webFragment));
            webFragment.titleWeb = "";
            webFragment.urlWeb = url;
        } else if (mode == 3) {
            imageView.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.action_mainFragment_to_webFragment));
            webFragment.titleWeb = "";
            webFragment.urlWeb = url;
        }else if (mode == 4) {
            int id_item = Integer.parseInt(url);
            imageView.setOnClickListener(view1 -> Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_detailsFragment));
            SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("DETAILS_ITEM", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("ID_ITEM", id_item);
            editor.apply();
        }

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

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
