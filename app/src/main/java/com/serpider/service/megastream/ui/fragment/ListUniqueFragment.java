package com.serpider.service.megastream.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.adapter.ItemAdapter;
import com.serpider.service.megastream.adapter.QualityAdapter;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.api.ApiServer;
import com.serpider.service.megastream.databinding.FragmentListUniqueBinding;
import com.serpider.service.megastream.interfaces.Key;
import com.serpider.service.megastream.model.Film;
import com.serpider.service.megastream.model.Movie;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListUniqueFragment extends Fragment {
    ApiInterFace requestList;
    ItemAdapter itemAdapter;
    List<Film> listItem = new ArrayList<>();
    RecyclerView recyclerList;
    FragmentListUniqueBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentListUniqueBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.btnBack.setOnClickListener(view1 -> getActivity().onBackPressed());

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DETAILS_ITEM", Context.MODE_PRIVATE);
        String groupType = sharedPreferences.getString("GROUP_TYPE", "");
        String groupName = sharedPreferences.getString("GROUP_NAME", "");
        String groupVector = sharedPreferences.getString("GROUP_VECTOR", "");

        mBinding.titleList.setText(groupName);
        if (!groupVector.trim().isEmpty()) {
            Glide.with(getActivity()).load(groupVector).into(mBinding.vectorList);

        }else {
            mBinding.cardVector.setVisibility(View.GONE);
        }

        loadData(groupType, groupName);

    }

    private void loadData(String groupType, String groupName) {

        requestList = ApiClinent.getApiClinent(getActivity(), Key.BASE_URL).create(ApiInterFace.class);
        recyclerList = mBinding.listRecyclerView;
        recyclerList.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        recyclerList.setLayoutManager(layoutManager);
        requestList.getItem(groupType, groupName, 0).enqueue(new Callback<List<Film>>() {
            @Override
            public void onResponse(Call<List<Film>> call, Response<List<Film>> response) {
                listItem = response.body();
                itemAdapter = new ItemAdapter(getActivity().getApplicationContext(), listItem, "LIST");
                recyclerList.setAdapter(itemAdapter);
                if (itemAdapter.getItemCount() == 0) {
                    mBinding.bodyEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Film>> call, Throwable t) {

            }
        });


    }
}