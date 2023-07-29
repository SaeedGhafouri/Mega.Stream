package com.serpider.service.megastream.ui;

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

import com.google.android.material.chip.Chip;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.adapter.ItemAdapter;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.api.ApiServer;
import com.serpider.service.megastream.databinding.FragmentSearchBinding;
import com.serpider.service.megastream.interfaces.Elements;
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
    private ChipAdapter adapter;
    private List<String> searchItems;
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

        historyOption();

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

                if (itemAdapter.getItemCount() == 0) {
                    mBinding.bodyEmpty.setVisibility(View.VISIBLE);
                }

                /*History*/
                searchItems.add(name);
                if (searchItems.size() > 10) {
                    searchItems.remove(0);
                }
                saveSearchHistory(searchItems);
                adapter.setItems(searchItems);
                adapter.notifyDataSetChanged();
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

    private void historyOption() {
        sharedPreferences = getActivity().getSharedPreferences("SearchHistory", Context.MODE_PRIVATE);

        searchItems = new ArrayList<>();
        adapter = new ChipAdapter(searchItems);
        mBinding.historyRecyclerView.setAdapter(adapter);

        List<String> savedSearchItems = getSearchHistory();
        searchItems.addAll(savedSearchItems);
        adapter.setItems(searchItems);
        adapter.notifyDataSetChanged();

    }

    private void saveSearchHistory(List<String> searchItems) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> searchSet = new HashSet<>(searchItems);
        editor.putStringSet("SearchItems", searchSet);
        editor.apply();
    }

    private List<String> getSearchHistory() {
        Set<String> searchSet = sharedPreferences.getStringSet("SearchItems", new HashSet<>());
        return new ArrayList<>(searchSet);
    }

    public class ChipAdapter extends RecyclerView.Adapter<ChipAdapter.ChipViewHolder> {

        private List<String> items;

        public class ChipViewHolder extends RecyclerView.ViewHolder {
            Chip chip;

            public ChipViewHolder(View view) {
                super(view);
                chip = view.findViewById(R.id.chip);
            }
        }

        public ChipAdapter(List<String> items) {
            this.items = items;
        }

        public void setItems(List<String> items) {
            this.items = items;
        }

        @Override
        public ChipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_search_history, parent, false);

            return new ChipViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ChipViewHolder holder, int position) {
            String item = items.get(position);
            holder.chip.setText(item);
            holder.chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Action to perform when a chip is clicked
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

}
}
