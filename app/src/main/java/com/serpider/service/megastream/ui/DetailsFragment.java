package com.serpider.service.megastream.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.adapter.QualityAdapter;
import com.serpider.service.megastream.adapter.Season;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.api.ApiServer;
import com.serpider.service.megastream.databinding.FragmentDetailsBinding;
import com.serpider.service.megastream.interfaces.Elements;
import com.serpider.service.megastream.model.Film;
import com.serpider.service.megastream.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsFragment extends Fragment {

    ApiInterFace requestFilm, requestSeason;
    String idUnique, urlTriler, typeItem;

    ApiInterFace requestUrl ;
    QualityAdapter qualityAdapter;
    List<Movie> listUrl = new ArrayList<>();
    List<Season> seasonList = new ArrayList<>();
    RecyclerView recyclerUrl;

    private TabLayout seasionTabLayout;
    private ViewPager seasionViewPager;

    FragmentDetailsBinding mBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentDetailsBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadData();

        /*Test Code*/
        mBinding.btnTest.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.action_detailsFragment_to_listUniqueFragment));

       mBinding.itemPoster.setOnClickListener(view1 -> qualitySheet());

       /*Triler*/
        mBinding.btnTriler.setOnClickListener(view1 -> Elements.DialogVideoPlayer(getActivity(),urlTriler));
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("DETAILS_ITEM", Context.MODE_PRIVATE);
        idUnique = sharedPreferences.getString("ID_ITEM", "0");
        Toast.makeText(getActivity(), idUnique, Toast.LENGTH_SHORT).show();

        requestFilm = ApiClinent.getApiClinent(ApiServer.urlData()).create(ApiInterFace.class);
        requestFilm.getFilmById(idUnique).enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                Film film = response.body();
                if(film != null){

                    mBinding.itemTitle.setText(film.getItem_title_en());
                    mBinding.itemTitleFa.setText(film.getItem_title_fa());
                    mBinding.itemYear.setText(film.getItem_year());
                    mBinding.itemCountry.setText(film.getItem_country());
                    mBinding.itemTime.setText(film.getItem_time());
                    mBinding.itemAge.setText(film.getItem_ages());
                    mBinding.itemImdb.setText(film.getItem_imdb());
                    mBinding.itemSynopsis.setText(film.getItem_synopsis());
                    mBinding.itemDesc.setText(film.getItem_desc());
                    urlTriler= film.getItem_trailer();
                    Picasso.get().load(film.getItem_poster()).into(mBinding.itemPoster);
                    Picasso.get().load(film.getItem_header()).into(mBinding.itemHeader);

                    typeItem= film.getItem_type();
                    if (typeItem.equals("Serial")){
                        serialModePlay(film.getItem_unique());
                    }else {
                        mBinding.seasionTabLayout.setVisibility(View.GONE);
                        mBinding.seasionViewPager.setVisibility(View.GONE);
                    }

                }

            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void qualitySheet() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.sheet_quality, null);
        BottomSheetDialog QualitySheet = new BottomSheetDialog(getActivity());
        QualitySheet.setContentView(view);
        QualitySheet.show();

        requestUrl = ApiClinent.getApiClinent(ApiServer.urlData()).create(ApiInterFace.class);
        recyclerUrl = view.findViewById(R.id.qualityRecycler);
        recyclerUrl.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerUrl.setLayoutManager(layoutManager);
        requestUrl.getMovieUrl("").enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                listUrl = response.body();
                qualityAdapter = new QualityAdapter(getActivity().getApplicationContext(), listUrl, getActivity());
                recyclerUrl.setAdapter(qualityAdapter);
            }
            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void serialModePlay(String idSerial) {

        MaterialButton btnPlay;
        btnPlay = getActivity().findViewById(R.id.btnPlay);

        btnPlay.setVisibility(View.GONE);
        btnPlay.setEnabled(false);

        /*Tab Setup*/
        seasionViewPager = mBinding.seasionViewPager;
        seasionTabLayout = mBinding.seasionTabLayout;
        requestSeason = ApiClinent.getApiClinent(ApiServer.urlData()).create(ApiInterFace.class);
        requestSeason.getSeason(idSerial).enqueue(new Callback<List<Season>>() {
            @Override
            public void onResponse(Call<List<Season>> call, Response<List<Season>> response) {
                seasonList = response.body();
                setupViewPager(seasionViewPager, 2, "" );
                mBinding.seasionTabLayout.setupWithViewPager(seasionViewPager);
            }

            @Override
            public void onFailure(Call<List<Season>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setupViewPager(ViewPager viewPager, int num, String title) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        for(int l=1; l<=num; l++){
            adapter.addFragment(new Fragment(), String.valueOf(l));
        }

        viewPager.setAdapter(adapter);
    }
    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> titleList = new ArrayList<>();
        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
        @Override
        public int getCount() {
            return fragmentList.size();
        }
        public void addFragment(Fragment fragment, String title){
            fragmentList.add(fragment);
            titleList.add(title);
        }
        public CharSequence getPageTitle(int position){
            return titleList.get(position);
        }
    }
}