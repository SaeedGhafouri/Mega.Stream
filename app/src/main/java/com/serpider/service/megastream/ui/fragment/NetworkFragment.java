package com.serpider.service.megastream.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.serpider.service.megastream.R;
import com.serpider.service.megastream.databinding.FragmentNetworkBinding;
import com.serpider.service.megastream.util.Connection;


public class NetworkFragment extends Fragment {

    private FragmentNetworkBinding mBinding;
    private Connection connection;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentNetworkBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        connection = new Connection();
        mBinding.btnTry.setOnClickListener(view1 -> {
            if (connection.isNetwork(getContext())){
                getActivity().onBackPressed();
            }
        });

    }
}