package com.serpider.service.megastream.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.adapter.GenreAdapter;
import com.serpider.service.megastream.adapter.GropingAdapter;
import com.serpider.service.megastream.adapter.ItemAdapter;
import com.serpider.service.megastream.adapter.NetworkAdapter;
import com.serpider.service.megastream.adapter.SliderAdapter;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.api.ApiServer;
import com.serpider.service.megastream.databinding.FragmentHomeBinding;
import com.serpider.service.megastream.interfaces.Key;
import com.serpider.service.megastream.model.Country;
import com.serpider.service.megastream.model.Film;
import com.serpider.service.megastream.model.Genre;
import com.serpider.service.megastream.model.Network;
import com.serpider.service.megastream.model.Ads;
import com.serpider.service.megastream.model.Result;
import com.serpider.service.megastream.ui.dialog.FilterFragment;
import com.serpider.service.megastream.util.DataSave;
import com.serpider.service.megastream.util.SnackBoard;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    RecyclerView recyclerFilm, recyclerCountry, recyclerGenre, recyclerNetwork;
    List<Film> listAllFilm = new ArrayList<>();
    List<Country> listCountry = new ArrayList<>();
    List<Genre> listGenre = new ArrayList<>();
    List<Network> listNetwork = new ArrayList<>();
    public ViewPager viewPager;
    List<Ads> adsList = new ArrayList<>();
    ItemAdapter itemAdapter;
    NetworkAdapter networkAdapter;
    SliderAdapter sliderAdapter;
    GropingAdapter gropingAdapter;
    GenreAdapter genreAdapter;
    /*Limit*/
    ApiInterFace requestSuggested, requestSerial;
    RecyclerView recyclerSuggested, recyclerSerial;
    List<Film> listSuggested = new ArrayList<>();
    List<Film> listSerial = new ArrayList<>();

    ApiInterFace requestAllFilm, requestCountry, requestSlider, requestGenre, requestNetwork;
    /*Animation Slider*/
    private Runnable runnable = null;
    public Handler handler = new Handler();
    FragmentHomeBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*loadCountry();
        loadNetwork();
        loadSlider();
        loadAllItem();
        loadLimitList();*/
        loadAds();
        loadGenres();
        loadCountrys();
        loadNetworks();

        /*loadCountrys();
        loadNetworks();
        loadAds();
        loadSuggested("item_genre", "پیشنهاد سردبیر");
        loadSerial("item_type", "Serial");*/

        /*Refresh Infomation*/
        mBinding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadGenres();
                loadCountrys();
                loadNetworks();
                loadAds();
                loadSuggested("item_genre", "پیشنهاد سردبیر");
                loadSerial("item_type", "Serial");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.refresh.setRefreshing(false);
                    }
                },2000);

            }
        });

       // mBinding.logoMain.setOnClickListener(view1 -> dialogFilter());
        mBinding.logoMain.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.action_mainFragment_to_detailsFragment));

        DataSave dataSave = new DataSave();

        mBinding.btnHomeSearch.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.action_mainFragment_to_searchFragment));

        mBinding.btnFilter.setOnClickListener(view1 -> dialogFilter());

    }

    /*BUG*/
    public void dialogFilter() {
        FilterFragment dialogFragment = new FilterFragment();
        dialogFragment.show(getActivity().getSupportFragmentManager(), "dialog_fragment_tag");

    }

    private void loadSuggested(String query, String name) {
        requestSuggested = ApiClinent.getApiClinent(getActivity(),Key.BASE_URL).create(ApiInterFace.class);
        recyclerSuggested = mBinding.recyclerSuggested;
        recyclerSuggested.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        recyclerSuggested.setLayoutManager(layoutManager);
        requestSuggested.getItem(query, name, 0).enqueue(new Callback<List<Film>>() {
            @Override
            public void onResponse(Call<List<Film>> call, Response<List<Film>> response) {
                listSuggested = response.body();
                itemAdapter = new ItemAdapter(getActivity().getApplicationContext(), listSuggested, "HOME");
                recyclerSuggested.setAdapter(itemAdapter);
            }

            @Override
            public void onFailure(Call<List<Film>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DETAILS_ITEM", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        mBinding.btnAllSuggested.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_listUniqueFragment);
            editor.putString("GROUP_TYPE", "item_genre");
            editor.putString("GROUP_NAME", name);
            editor.putString("GROUP_VECTOR", "");
            editor.apply();
        });

    }
    private void loadSerial(String name, String query) {
        requestSerial = ApiClinent.getApiClinent(getActivity(),Key.BASE_URL).create(ApiInterFace.class);
        recyclerSerial = mBinding.recyclerSerial;
        recyclerSerial.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        recyclerSerial.setLayoutManager(layoutManager);
        requestSerial.getItem(name, query, 10).enqueue(new Callback<List<Film>>() {
            @Override
            public void onResponse(Call<List<Film>> call, Response<List<Film>> response) {
                listSerial = response.body();
                itemAdapter = new ItemAdapter(getActivity().getApplicationContext(), listSerial, "HOME");
                recyclerSerial.setAdapter(itemAdapter);
            }
            @Override
            public void onFailure(Call<List<Film>> call, Throwable t) {
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DETAILS_ITEM", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        mBinding.btnAllSuggested.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_listUniqueFragment);
            editor.putString("GROUP_TYPE", "item_genre");
            editor.putString("GROUP_NAME", name);
            editor.putString("GROUP_VECTOR", "");
            editor.apply();
        });

    }

    /*New Api Data*/
    private void loadAllItem(){


    }
    private void loadGenres(){
        requestGenre = ApiClinent.getApiClinent(getActivity(), Key.BASE_URL).create(ApiInterFace.class);
        recyclerGenre = mBinding.recyclerGenre;
        recyclerGenre.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        recyclerGenre.setLayoutManager(layoutManager);
        requestGenre.getGenre().enqueue(new Callback<List<Genre>>() {
            @Override
            public void onResponse(Call<List<Genre>> call, Response<List<Genre>> response) {
                listGenre = response.body();
                genreAdapter = new GenreAdapter(getActivity() , listGenre, getActivity());
                recyclerGenre.setAdapter(genreAdapter);

            }

            @Override
            public void onFailure(Call<List<Genre>> call, Throwable t) {
                SnackBoard.show(getActivity(),"خطای سمت سرور", 0);
            }
        });

    }
    private void loadCountrys() {
        requestCountry = ApiClinent.getApiClinent(getActivity(), Key.BASE_URL).create(ApiInterFace.class);
        recyclerCountry = mBinding.recyclerCountry;
        recyclerCountry.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        recyclerCountry.setLayoutManager(layoutManager);
        requestCountry.getCountry().enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                listCountry = response.body();
                gropingAdapter = new GropingAdapter(getActivity().getApplicationContext(), listCountry);
                recyclerCountry.setAdapter(gropingAdapter);

            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
            }
        });

    }
    private void loadNetworks() {
        requestNetwork = ApiClinent.getApiClinent(getActivity(),ApiServer.urlData()).create(ApiInterFace.class);
        recyclerNetwork = mBinding.recyclerNetwork;
        recyclerNetwork.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        recyclerNetwork.setLayoutManager(layoutManager);
        requestNetwork.getNetwork().enqueue(new Callback<List<Network>>() {
            @Override
            public void onResponse(Call<List<Network>> call, Response<List<Network>> response) {
                listNetwork = response.body();
                networkAdapter = new NetworkAdapter(getContext(), listNetwork);
                recyclerNetwork.setAdapter(networkAdapter);
            }
            @Override
            public void onFailure(Call<List<Network>> call, Throwable t) {
            }
        });
    }
    private void loadAds() {
        requestSlider = ApiClinent.getApiClinent(getActivity(),Key.BASE_URL).create(ApiInterFace.class);
        viewPager = mBinding.sliderMain;
        requestSlider.getAds().enqueue(new Callback<List<Ads>>() {
            @Override
            public void onResponse(Call<List<Ads>> call, Response<List<Ads>> response) {
                adsList = response.body();
                sliderAdapter = new SliderAdapter(getContext(), getActivity().getLayoutInflater(), adsList, getActivity());
                viewPager.setAdapter(sliderAdapter);
                mBinding.indicatorSlider.setViewPager(viewPager);
                sliderAdapter.registerDataSetObserver(mBinding.indicatorSlider.getDataSetObserver());
            }
            @Override
            public void onFailure(Call<List<Ads>> call, Throwable t) {
            }
        });
    }

}