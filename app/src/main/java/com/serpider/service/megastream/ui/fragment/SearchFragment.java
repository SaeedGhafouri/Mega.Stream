package com.serpider.service.megastream.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.adapter.ItemAdapter;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.api.ApiServer;
import com.serpider.service.megastream.databinding.FragmentSearchBinding;
import com.serpider.service.megastream.interfaces.Elements;
import com.serpider.service.megastream.interfaces.Key;
import com.serpider.service.megastream.model.Film;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    private ApiInterFace requestSearch;
    private ItemAdapter itemAdapter;
    List<Film> listSearch = new ArrayList<>();
    RecyclerView recyclerSearch;
    String name;
    private SharedPreferences sharedPreferences;
    FragmentSearchBinding mBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentSearchBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Animation slideDownAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.down);
        mBinding.bodySearch.startAnimation(slideDownAnimation);

        mBinding.edSearch.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mBinding.edSearch, InputMethodManager.SHOW_IMPLICIT);

        mBinding.edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                mBinding.loaderSearch.setVisibility(View.VISIBLE);
                mBinding.btnSearch.setVisibility(View.GONE);
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getSearch();
                    mBinding.loaderSearch.setVisibility(View.VISIBLE);
                    mBinding.btnSearch.setVisibility(View.GONE);
                    return true;
                }else {
                    mBinding.loaderSearch.setVisibility(View.GONE);
                    mBinding.btnSearch.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        mBinding.btnSearch.setOnClickListener(view1 -> getSearch());

    }
    private void getSearch() {
        mBinding.loaderSearch.setVisibility(View.VISIBLE);
        mBinding.btnSearch.setVisibility(View.GONE);
        name = mBinding.edSearch.getText().toString().trim();
        requestSearch = ApiClinent.getApiClinent(getActivity(), Key.BASE_URL).create(ApiInterFace.class);

        recyclerSearch = mBinding.recyclerSearch;
        recyclerSearch.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        recyclerSearch.setLayoutManager(layoutManager);

        requestSearch.getFilmSearch(name).enqueue(new Callback<List<Film>>() {
            @Override
            public void onResponse(Call<List<Film>> call, Response<List<Film>> response) {
                listSearch = response.body();
                itemAdapter = new ItemAdapter(getActivity().getApplicationContext(), listSearch, "SEARCH");
                recyclerSearch.setAdapter(itemAdapter);

                mBinding.loaderSearch.setVisibility(View.GONE);
                mBinding.btnSearch.setVisibility(View.VISIBLE);

                if (itemAdapter.getItemCount() == 0) {
                    mBinding.bodyEmpty.setVisibility(View.VISIBLE);
                }else {
                    mBinding.bodyEmpty.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<List<Film>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getSearch();
    }

}
