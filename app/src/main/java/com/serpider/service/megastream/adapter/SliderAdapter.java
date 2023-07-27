package com.serpider.service.megastream.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.button.MaterialButton;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.ui.WebFragment;
import com.serpider.service.megastream.model.Ads;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private View view;
    private ScaleGestureDetector mScaleGestureDetector;
    public ImageView imageView;
    public TextView titleSlider, descSlider;
    private FragmentActivity fragmentActivity;
    private MaterialButton btnSlider;
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
        titleSlider = view.findViewById(R.id.titleSlider);
        descSlider = view.findViewById(R.id.descSlider);
        btnSlider = view.findViewById(R.id.btnSlider);
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
        // imageView.setImageResource();
        Picasso.get().load(data.get(position).getBanner()).into(imageView);
        titleSlider.setText(data.get(position).getTitle());
        descSlider.setText(data.get(position).getCaption());

        btnSlider.setBackgroundColor( ContextCompat.getColor(fragmentActivity, R.color.purple_200));

        /*Check Mode*/
        int MODE = data.get(position).getType();
        String BTN_TEXT = data.get(position).getButton_text();
        String BTN_COLOR = data.get(position).getButton_bg();
        String LINK = data.get(position).getLink();
        changeMode(MODE, BTN_TEXT, BTN_COLOR, LINK);

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    private void changeMode(int mode, String btnText, String btnColor, String url) {

        /*
        1 = Donate
        2 = Ads
        3 = Web
        4 = Watch
        */
        if (mode == 1) {
            btnSlider.setText(btnText);
            view.setOnClickListener(view1 -> Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_donateFragment));
        } else if (mode == 2) {
            btnSlider.setText(btnText);
            view.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.action_mainFragment_to_webFragment));
            webFragment.titleWeb = "";
            webFragment.urlWeb = url;
        } else if (mode == 3) {
            view.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.action_mainFragment_to_webFragment));
            webFragment.titleWeb = "";
            webFragment.urlWeb = url;
            btnSlider.setText(btnText);
        }else if (mode == 4) {
            btnSlider.setText("تماشا");
            String item_unique = url;
     //       Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_detailsFragment);
            SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("DETAILS_ITEM", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("ID_ITEM", item_unique);
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
