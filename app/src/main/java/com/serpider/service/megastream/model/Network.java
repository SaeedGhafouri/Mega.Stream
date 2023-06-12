package com.serpider.service.megastream.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.serpider.service.megastream.adapter.ItemAdapter;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.api.ApiServer;
import com.serpider.service.megastream.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Network {

    private String network_unique, network_name, network_desc, network_image;

    public Network(String network_unique, String network_name, String network_desc, String network_image) {
        this.network_unique = network_unique;
        this.network_name = network_name;
        this.network_desc = network_desc;
        this.network_image = network_image;
    }

    public String getNetwork_unique() {
        return network_unique;
    }

    public void setNetwork_unique(String network_unique) {
        this.network_unique = network_unique;
    }

    public String getNetwork_name() {
        return network_name;
    }

    public void setNetwork_name(String network_name) {
        this.network_name = network_name;
    }

    public String getNetwork_desc() {
        return network_desc;
    }

    public void setNetwork_desc(String network_desc) {
        this.network_desc = network_desc;
    }

    public String getNetwork_image() {
        return network_image;
    }

    public void setNetwork_image(String network_image) {
        this.network_image = network_image;
    }

    public static class DataSave {

        public void UserIdSave(Context context, String unique) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("USER_UNIQUE", unique);
            editor.apply();
        }
        public String UserGetId(Context context) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
            return sharedPreferences.getString("USER_UNIQUE", "");
        }

    }

    public static class SearchFragment extends Fragment {
        ApiInterFace requestSearch;
        ItemAdapter itemAdapter;
        List<Film> listSearch = new ArrayList<>();
        RecyclerView recyclerSearch;
        String name;
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

            mBinding.edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        getSearch();
                        return true;
                    }
                    return false;
                }
            });

            mBinding.btnSearch.setOnClickListener(view1 -> getSearch());

        }

        private void getSearch() {
            name = mBinding.edSearch.getText().toString().trim();
            requestSearch = ApiClinent.getApiClinent(getActivity(), ApiServer.urlData()).create(ApiInterFace.class);

            recyclerSearch = mBinding.recyclerSearch;
            recyclerSearch.setHasFixedSize(true);
            GridLayoutManager layoutManager =
                    new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
            recyclerSearch.setLayoutManager(layoutManager);

            requestSearch.getSearch(name).enqueue(new Callback<List<Film>>() {
                @Override
                public void onResponse(Call<List<Film>> call, Response<List<Film>> response) {
                    listSearch = response.body();
                    itemAdapter = new ItemAdapter(getActivity().getApplicationContext(), listSearch, "SEARCH");
                    recyclerSearch.setAdapter(itemAdapter);
                }

                @Override
                public void onFailure(Call<List<Film>> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }

        @Override
        public void onResume() {
            super.onResume();

            getSearch();

        }
    }
}
