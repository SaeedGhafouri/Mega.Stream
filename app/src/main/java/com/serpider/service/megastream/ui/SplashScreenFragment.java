package com.serpider.service.megastream.ui;

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

import com.serpider.service.megastream.R;
import com.serpider.service.megastream.databinding.FragmentSplashScreenBinding;

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

        /*Load Animation Logo*/
        mBinding.logoSplash.setVisibility(View.GONE);
        Handler handler1 = new Handler();
        handler1.postDelayed(() -> {
            mBinding.logoSplash.setVisibility(View.VISIBLE);
            animLogo();
        },1000);


        Handler handler2 = new Handler();
        handler2.postDelayed(() -> {
            Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_loginFragment);
        },3000);

    }

    private void animLogo() {
        animLogo = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        mBinding.logoSplash.startAnimation(animLogo);
    }
}