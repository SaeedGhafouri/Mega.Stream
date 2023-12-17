package com.serpider.service.megastream.ui.fragment;

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
import com.serpider.service.megastream.adapter.GenreAdapter;
import com.serpider.service.megastream.adapter.ItemAdapter;
import com.serpider.service.megastream.adapter.SubscriptionAdapter;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.databinding.FragmentSubscriptionBinding;
import com.serpider.service.megastream.interfaces.Key;
import com.serpider.service.megastream.model.Film;
import com.serpider.service.megastream.model.Subscription;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionFragment extends Fragment {
    private FragmentSubscriptionBinding binding;
    private SubscriptionAdapter adapter;
    /*Limit*/
    private ApiInterFace requestSubscrition;
    private RecyclerView recyclerSubscription;
    private ApiInterFace request;
    private List<Subscription> listSubscription = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSubscriptionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadSubscription();

    }

    private void loadSubscription() {
        request = ApiClinent.getApiClinent(getActivity(), Key.BASE_URL).create(ApiInterFace.class);
        recyclerSubscription = binding.recyclerSubscription;
        recyclerSubscription.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerSubscription.setLayoutManager(layoutManager);

        request.getSubAll().enqueue(new Callback<List<Subscription>>() {
            @Override
            public void onResponse(Call<List<Subscription>> call, Response<List<Subscription>> response) {
                listSubscription = response.body();
                adapter = new SubscriptionAdapter(getActivity(), listSubscription);
                recyclerSubscription.setAdapter(adapter);
                if (listSubscription.size() > 0) {
                    binding.recyclerSubscription.setVisibility(View.VISIBLE);
                    binding.loader.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Subscription>> call, Throwable t) {
                binding.recyclerSubscription.setVisibility(View.GONE);
                binding.loader.setVisibility(View.GONE);
            }
        });

    }
}