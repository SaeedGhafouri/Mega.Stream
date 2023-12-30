package com.serpider.service.megastream.ui.fragment;

import static com.serpider.service.megastream.util.DataSave.COUNT_ITEM;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
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
import com.serpider.service.megastream.interfaces.Elements;
import com.serpider.service.megastream.interfaces.Key;
import com.serpider.service.megastream.model.Country;
import com.serpider.service.megastream.model.Film;
import com.serpider.service.megastream.model.Genre;
import com.serpider.service.megastream.model.Network;
import com.serpider.service.megastream.model.Ads;
import com.serpider.service.megastream.model.Result;
import com.serpider.service.megastream.ui.dialog.FilterFragment;
import com.serpider.service.megastream.util.DataSave;

import com.serpider.service.megastream.util.Toaster;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerFilm, recyclerCountry, recyclerGenre, recyclerNetwork;
    private List<Film> listAllFilm = new ArrayList<>();
    private List<Country> listCountry = new ArrayList<>();
    private List<Genre> listGenre = new ArrayList<>();
    private List<Network> listNetwork = new ArrayList<>();
    public ViewPager viewPager;
    private List<Ads> adsList = new ArrayList<>();
    private ItemAdapter itemAdapter;
    private NetworkAdapter networkAdapter;
    private SliderAdapter sliderAdapter;
    private GropingAdapter gropingAdapter;
    private GenreAdapter genreAdapter;
    /*Limit*/
    private ApiInterFace request;
    private RecyclerView recyclerSuggested, recyclerSerial;
    private List<Film> listSuggested = new ArrayList<>();
    private List<Film> listSerial = new ArrayList<>();

    /*Animation Slider*/
    private Runnable runnable = null;
    public Handler handler = new Handler();
    private FragmentHomeBinding mBinding;
    private static boolean deepLinkHandled = false;
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
        deeplink();
        request = ApiClinent.getApiClinent(getActivity(),Key.BASE_URL).create(ApiInterFace.class);

        loadAds();
        loadGenres();
        loadCountrys();
        loadNetworks();
        loadSuggested("item_suggestion", "1");
        loadSerial("2", "item_type");

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
        mBinding.logoMain.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.action_mainFragment_to_subscriptionFragment));

        mBinding.btnHomeSearch.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.action_mainFragment_to_searchFragment));

        mBinding.btnFilter.setOnClickListener(view1 -> dialogFilter());

        mBinding.numberAllItem.setText(String.valueOf(DataSave.getSharedPreferences(getActivity()).getInt(COUNT_ITEM, 0)));
    }
    private void deeplink() {
        if (!deepLinkHandled) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("DETAILS_ITEM", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Uri uri = getActivity().getIntent().getData();
            if (uri != null) {
                List<String> params = uri.getPathSegments();
                String id = params.get(params.size() - 1);
                int item_unique = Integer.parseInt(id);
                editor.putInt("ID_ITEM", item_unique);
                editor.apply();
                Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_detailsFragment);
                deepLinkHandled = true;
            }
        }
    }
    /*BUG*/
    public void dialogFilter() {
        FilterFragment dialogFragment = new FilterFragment();
        dialogFragment.show(getActivity().getSupportFragmentManager(), "dialog_fragment_tag");
    }
    /*New Api Data*/
    private void loadSuggested(String query, String name) {
        recyclerSuggested = mBinding.recyclerSuggested;
        recyclerSuggested.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        recyclerSuggested.setLayoutManager(layoutManager);
        request.getFilmBy(query, name, 10).enqueue(new Callback<List<Film>>() {
            @Override
            public void onResponse(Call<List<Film>> call, Response<List<Film>> response) {
                listSuggested = response.body();
                itemAdapter = new ItemAdapter(getActivity(), listSuggested, "HOME");
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
            editor.putString("GROUP_QUERY", query);
            editor.putString("GROUP_NAME", name);
            editor.putString("GROUP_TITLE", "پیشنهاد سردبیر");
            editor.putString("GROUP_VECTOR", "");
            editor.apply();
        });
    }
    private void loadSerial(String query, String name) {
        recyclerSerial = mBinding.recyclerSerial;
        recyclerSerial.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        recyclerSerial.setLayoutManager(layoutManager);
        request.getFilmBy(query, name, 10).enqueue(new Callback<List<Film>>() {
            @Override
            public void onResponse(Call<List<Film>> call, Response<List<Film>> response) {
                listSerial = response.body();
                itemAdapter = new ItemAdapter(getActivity(), listSerial, "HOME");
                recyclerSerial.setAdapter(itemAdapter);
            }
            @Override
            public void onFailure(Call<List<Film>> call, Throwable t) {
            }
        });
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DETAILS_ITEM", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        mBinding.btnAllSerial.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_listUniqueFragment);
            editor.putString("GROUP_QUERY", query);
            editor.putString("GROUP_NAME", "1");
            editor.putString("GROUP_TITLE", "سریال ها");
            editor.putString("GROUP_VECTOR", "");
            editor.apply();
        });
    }
    private void loadGenres(){
        recyclerGenre = mBinding.recyclerGenre;
        recyclerGenre.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        recyclerGenre.setLayoutManager(layoutManager);
        request.getGenre().enqueue(new Callback<List<Genre>>() {
            @Override
            public void onResponse(Call<List<Genre>> call, Response<List<Genre>> response) {
                listGenre = response.body();
                genreAdapter = new GenreAdapter(getActivity() , listGenre, getActivity());
                recyclerGenre.setAdapter(genreAdapter);
            }
            @Override
            public void onFailure(Call<List<Genre>> call, Throwable t) {
                Toaster.error(getActivity(), "خطای سمت سرور", Toast.LENGTH_LONG);
                
            }
        });
    }
    private void loadCountrys() {
        recyclerCountry = mBinding.recyclerCountry;
        recyclerCountry.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        recyclerCountry.setLayoutManager(layoutManager);
        request.getCountry().enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                listCountry = response.body();
                gropingAdapter = new GropingAdapter(getActivity(), listCountry);
                recyclerCountry.setAdapter(gropingAdapter);

            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
            }
        });
    }
    private void loadNetworks() {
        recyclerNetwork = mBinding.recyclerNetwork;
        recyclerNetwork.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        recyclerNetwork.setLayoutManager(layoutManager);
        request.getNetwork().enqueue(new Callback<List<Network>>() {
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
        viewPager = mBinding.sliderMain;
        request.getAds().enqueue(new Callback<List<Ads>>() {
            @Override
            public void onResponse(Call<List<Ads>> call, Response<List<Ads>> response) {
                adsList = response.body();
                sliderAdapter = new SliderAdapter(getActivity(), adsList, mBinding.sliderMain);
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