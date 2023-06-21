package com.serpider.service.megastream.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.serpider.service.megastream.R;
import com.serpider.service.megastream.adapter.CategoryAdapter;
import com.serpider.service.megastream.adapter.GenreAdapter;
import com.serpider.service.megastream.adapter.ItemAdapter;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.api.ApiServer;
import com.serpider.service.megastream.databinding.FragmentCategoryBinding;
import com.serpider.service.megastream.model.Genre;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {
    List<Genre> listCategory = new ArrayList<>();
    CategoryAdapter categoryAdapter;
    RecyclerView recyclerView;
    ApiInterFace requestCategor;

    FragmentCategoryBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentCategoryBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadCategory();

    }

    private void loadCategory() {

        requestCategor = ApiClinent.getApiClinent(getActivity(), ApiServer.urlData()).create(ApiInterFace.class);
        recyclerView = mBinding.listRecyclerView;
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        requestCategor.getGenre().enqueue(new Callback<List<Genre>>() {
            @Override
            public void onResponse(Call<List<Genre>> call, Response<List<Genre>> response) {

                listCategory = response.body();
                categoryAdapter = new CategoryAdapter(getActivity().getApplicationContext(), listCategory);
                recyclerView.setAdapter(categoryAdapter);

            }

            @Override
            public void onFailure(Call<List<Genre>> call, Throwable t) {

            }
        });

    }
}