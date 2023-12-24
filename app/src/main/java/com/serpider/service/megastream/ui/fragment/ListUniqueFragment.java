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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListUniqueFragment extends Fragment {
    private ApiInterFace requestList;
    private ItemAdapter itemAdapter;
    private List<Film> listItem = new ArrayList<>();
    private RecyclerView recyclerList;
    private FragmentListUniqueBinding mBinding;
    private int currentPage = 1;
    private int itemsPerPage = 24;
    private boolean isLoading = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentListUniqueBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.btnBack.setOnClickListener(view1 -> getActivity().onBackPressed());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DETAILS_ITEM", Context.MODE_PRIVATE);
        String groupQuery = sharedPreferences.getString("GROUP_QUERY", "");
        String groupName = sharedPreferences.getString("GROUP_NAME", "");
        String groupTitle = sharedPreferences.getString("GROUP_TITLE", "");
        String groupVector = sharedPreferences.getString("GROUP_VECTOR", "");
        mBinding.titleList.setText(groupTitle);
        if (!groupVector.trim().isEmpty()) {
            Glide.with(getActivity()).load(groupVector).into(mBinding.vectorList);
        }else {
            mBinding.cardVector.setVisibility(View.GONE);
        }
        loadData(groupQuery, groupName);
    }
    private void loadData(String query, String name) {
        mBinding.loader.setVisibility(View.VISIBLE);
        requestList = ApiClinent.getApiClinent(getActivity(), Key.BASE_URL).create(ApiInterFace.class);
        recyclerList = mBinding.listRecyclerView;
        recyclerList.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        recyclerList.setLayoutManager(layoutManager);

        itemAdapter = new ItemAdapter(getActivity().getApplicationContext(), new ArrayList<>(), "LIST");
        recyclerList.setAdapter(itemAdapter);

        recyclerList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) { // اگر اسکرول به پایین انجام شده باشد
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (!isLoading && (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        currentPage++;
                        loadPaginatedData(query, name, currentPage, itemsPerPage);
                    }
                }
            }
        });

        loadPaginatedData(query, name, currentPage, itemsPerPage);
    }
    private void loadPaginatedData(String query, String name, int page, int perPage) {
        isLoading = true;
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("ITEM_QUERY", query);
        requestBody.put("ITEM_NAME", name);
        requestList.getFilmListBy(page, perPage, requestBody).enqueue(new Callback<List<Film>>() {
            @Override
            public void onResponse(Call<List<Film>> call, Response<List<Film>> response) {
                isLoading = false;
                if (response.isSuccessful() && response.body() != null) {
                    if (page == 1) {
                        itemAdapter.setList(response.body());
                    } else {
                        itemAdapter.addItems(response.body());
                    }
                    mBinding.loader.setVisibility(View.GONE);
                    mBinding.listRecyclerView.setVisibility(View.VISIBLE);
                    mBinding.bodyEmpty.setVisibility(itemAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Film>> call, Throwable t) {
                isLoading = false;
                // پیاده‌سازی مدیریت خطا (بر اساس نیاز)
            }
        });
    }
}