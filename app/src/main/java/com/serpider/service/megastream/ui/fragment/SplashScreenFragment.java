package com.serpider.service.megastream.ui.fragment;

import static com.serpider.service.megastream.util.DataSave.getIsReady;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.databinding.FragmentSplashScreenBinding;
import com.serpider.service.megastream.util.Connection;

public class SplashScreenFragment extends Fragment {
    FragmentSplashScreenBinding mBinding;
    Animation animLogo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentSplashScreenBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Shimmer txtShimmer = new Shimmer();
        /*Load Animation Logo*/
        mBinding.logoSplash.setVisibility(View.GONE);
        Handler handler1 = new Handler();
        handler1.postDelayed(() -> {
            mBinding.logoSplash.setVisibility(View.VISIBLE);
            txtShimmer.start(mBinding.txtSplash);
            animLogo();
        },1000);


        if (new Connection().isNetwork(getContext())) {
            Handler handler2 = new Handler();
            handler2.postDelayed(() -> {
                if (getIsReady(getActivity())){
                    Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_mainFragment);
                }else {
                    Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_wellcomeFragment);
                }

            },3000);
        }else {
            new Connection().showView(view, R.id.action_splashScreenFragment_to_networkFragment);
        }

    }

    private void animLogo() {
        animLogo = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        mBinding.logoSplash.startAnimation(animLogo);
    }
}