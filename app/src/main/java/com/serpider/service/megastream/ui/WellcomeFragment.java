package com.serpider.service.megastream.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.serpider.service.megastream.R;
import com.serpider.service.megastream.databinding.FragmentWellcomeBinding;
import com.serpider.service.megastream.util.Connection;

public class WellcomeFragment extends Fragment {
    FragmentWellcomeBinding mBinding;
    private Animation animation;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentWellcomeBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadBgAnim();

      //  mBinding.btnSkip.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.wel));
        mBinding.sliderWellcome.setAdapter(new CustomPagerAdapter(getActivity()));

        mBinding.btnSkip.setOnClickListener(view1 -> {
            if (new Connection().isNetwork(getActivity())) {
                Navigation.findNavController(view1).navigate(R.id.action_wellcomeFragment_to_mainFragment);
            }else {
                new Connection().showDialog(getActivity());
            }
        });

    }

    private void loadBgAnim() {

        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.shadow);
        mBinding.shadowOne.startAnimation(animation);
        mBinding.shadowTwo.startAnimation(animation);

    }

    public class CustomPagerAdapter extends PagerAdapter {
        private Context mContext;
        public CustomPagerAdapter(Context context) {
            mContext = context;
        }
        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            ModelObject modelObject = ModelObject.values()[position];
            LayoutInflater inflater = LayoutInflater.from(mContext);
            ViewGroup layout = (ViewGroup) inflater.inflate(modelObject.getLayoutResId(), collection, false);
            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return ModelObject.values().length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    public enum ModelObject {

        ONE(R.layout.viewpager_first),
        TWO(R.layout.viewpager_second),
        TREE(R.layout.viewpager_third);

        private int mLayoutResId;

        ModelObject(int layoutResId) {
            mLayoutResId = layoutResId;
        }

        public int getLayoutResId() {
            return mLayoutResId;
        }

    }
}