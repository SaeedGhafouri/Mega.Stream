package com.serpider.service.megastream.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.databinding.FragmentMainBinding;
import com.serpider.service.megastream.ui.HomeFragment;


public class MainFragment extends Fragment {
    private int count = 0;
    FragmentMainBinding mBinding;
    private MainFragment mainFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentMainBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpNavigation();


    }

    public void setUpNavigation(){
        mBinding.bottomNavigation.setOnNavigationItemSelectedListener(selectedListener);
        mBinding.bottomNavigation.setSelectedItemId(R.id.home_desk);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            count++;
            if (count > 2){
                count = 0;
                return true;
            }
            switch (item.getItemId()) {

                case R.id.home_desk:
                    HomeFragment fragmentExplore = new HomeFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, fragmentExplore, "");
                    fragmentTransaction.commit();
                    return true;

                case R.id.category_desk:
                    CategoryFragment fragmentCategory = new CategoryFragment();
                    FragmentTransaction categoryTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    categoryTransaction.replace(R.id.content, fragmentCategory, "");
                    categoryTransaction.commit();
                    return true;

                case R.id.profile_desk:
                    ProfileFragment fragmentProfile = new ProfileFragment();
                    FragmentTransaction profileTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    profileTransaction.replace(R.id.content, fragmentProfile, "");
                    profileTransaction.commit();
                    return true;

            }
            return true;
        }
    };

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}