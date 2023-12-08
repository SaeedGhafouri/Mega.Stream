package com.serpider.service.megastream.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.databinding.FragmentSettingsBinding;


public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentSettingsBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.btnBack.setOnClickListener(view1 -> getActivity().onBackPressed());

        mBinding.btnInfo.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putInt("viewBody", 1);
            Navigation.findNavController(view1).navigate(R.id.action_settingsFragment_to_infoUserFragment, bundle);
        });
        mBinding.btnPassword.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putInt("viewBody", 2);
            Navigation.findNavController(view1).navigate(R.id.action_settingsFragment_to_infoUserFragment, bundle);
        });
        mBinding.btnCheckUpdate.setOnClickListener(view1 -> sheetUpdate());

    }

    private void sheetUpdate() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.sheet_update, null);
        BottomSheetDialog updateSheet = new BottomSheetDialog(getActivity());
        updateSheet.setContentView(view);
        updateSheet.show();

    }
}