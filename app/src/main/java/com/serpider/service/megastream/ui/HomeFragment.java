package com.serpider.service.megastream.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
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
import com.serpider.service.megastream.model.Country;
import com.serpider.service.megastream.model.Film;
import com.serpider.service.megastream.model.Genre;
import com.serpider.service.megastream.model.Network;
import com.serpider.service.megastream.model.Slider;

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
    List<Slider> sliderList = new ArrayList<>();
    ItemAdapter itemAdapter;
    NetworkAdapter networkAdapter;
    SliderAdapter sliderAdapter;
    GropingAdapter gropingAdapter;
    GenreAdapter genreAdapter;
    public int NumberAllItem;


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

        loadGenre();
        loadCountry();
        loadNetwork();
        loadSlider();
        loadAllItem();
        Network.DataSave dataSave = new Network.DataSave();
        Toast.makeText(getActivity(), "Id: " + dataSave.UserGetId(getContext()), Toast.LENGTH_SHORT).show();

        mBinding.btnHomeSearch.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.action_mainFragment_to_searchFragment));

    }

    private void loadNetwork() {

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
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadGenre() {

        requestGenre = ApiClinent.getApiClinent(getActivity(),ApiServer.urlData()).create(ApiInterFace.class);
        recyclerGenre = mBinding.recyclerGenre;
        recyclerGenre.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        recyclerGenre.setLayoutManager(layoutManager);
        requestGenre.getGenre().enqueue(new Callback<List<Genre>>() {
            @Override
            public void onResponse(Call<List<Genre>> call, Response<List<Genre>> response) {
                listGenre = response.body();
                genreAdapter = new GenreAdapter(getActivity().getApplicationContext(), listGenre);
                recyclerGenre.setAdapter(genreAdapter);
            }
            @Override
            public void onFailure(Call<List<Genre>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*Get All Country*/
    private void loadCountry() {

        requestCountry = ApiClinent.getApiClinent(getActivity(),ApiServer.urlData()).create(ApiInterFace.class);
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
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*Get All Item List*/
    private void loadAllItem() {

        requestAllFilm = ApiClinent.getApiClinent(getActivity(),ApiServer.urlData()).create(ApiInterFace.class);
        recyclerFilm = mBinding.recyclerItem;
        recyclerFilm.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        recyclerFilm.setLayoutManager(layoutManager);
        requestAllFilm.getAllFilm().enqueue(new Callback<List<Film>>() {
            @Override
            public void onResponse(Call<List<Film>> call, Response<List<Film>> response) {
                listAllFilm = response.body();
                itemAdapter = new ItemAdapter(getActivity().getApplicationContext(), listAllFilm, "HOME");
                recyclerFilm.setAdapter(itemAdapter);
                int itemSize = listAllFilm.size();
                int subSize = (int) (itemSize * 1.8);
                if (!listAllFilm.isEmpty()) {
                    mBinding.numberAllItem.setText(String.valueOf(subSize));
                }
            }
            @Override
            public void onFailure(Call<List<Film>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
    private void loadSlider() {

        requestSlider = ApiClinent.getApiClinent(getActivity(),ApiServer.urlData()).create(ApiInterFace.class);
        viewPager = mBinding.sliderMain;
        requestSlider.getSlider().enqueue(new Callback<List<Slider>>() {
            @Override
            public void onResponse(Call<List<Slider>> call, Response<List<Slider>> response) {
                sliderList = response.body();
                sliderAdapter = new SliderAdapter(getContext(), getLayoutInflater(), sliderList);
                viewPager.setAdapter(sliderAdapter);
                mBinding.indicatorSlider.setViewPager(viewPager);
                sliderAdapter.registerDataSetObserver(mBinding.indicatorSlider.getDataSetObserver());
            }

            @Override
            public void onFailure(Call<List<Slider>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //startAutoSlider(sliderAdapter.getCount());

    }
    private void startAutoSlider(final int count) {
        runnable = new Runnable() {
            @Override
            public void run() {
                int pos = viewPager.getCurrentItem();
                pos = pos + 1;
                if (pos >= count) pos = 0;
                viewPager.setCurrentItem(pos);
                handler.postDelayed(runnable, 3000);
            }
        };
        handler.postDelayed(runnable, 6000);

    }

}