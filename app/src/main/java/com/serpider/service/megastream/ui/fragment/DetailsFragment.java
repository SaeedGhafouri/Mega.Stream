package com.serpider.service.megastream.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.adapter.QualityAdapter;
import com.serpider.service.megastream.adapter.SeasonAdapter;
import com.serpider.service.megastream.database.DatabaseClient;
import com.serpider.service.megastream.model.Favorites;
import com.serpider.service.megastream.model.Movie_Play;
import com.serpider.service.megastream.model.Season;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.api.ApiServer;
import com.serpider.service.megastream.databinding.FragmentDetailsBinding;
import com.serpider.service.megastream.interfaces.Elements;
import com.serpider.service.megastream.model.Film;
import com.serpider.service.megastream.util.LoaderFullScreen;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsFragment extends Fragment {
    ApiInterFace requestFilm, requestSeason;
    String idUnique, urlTriler, typeItem;
    SeasonAdapter seasonAdapter;
    ApiInterFace requestUrl ;
    QualityAdapter qualityAdapter;
    List<Movie_Play> listUrl = new ArrayList<>();
    List<Season> seasonList = new ArrayList<>();
    RecyclerView recyclerUrl, recyclerSeason;
    MaterialButton btnPlay;
    FragmentDetailsBinding mBinding;
    private LoaderFullScreen loader;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentDetailsBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loader = LoaderFullScreen.getInstance(getContext());
        loader.show();

        loadData();
        /*Test Code*/
        btnPlay = getActivity().findViewById(R.id.btnPlay);
        mBinding.btnComment.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.action_detailsFragment_to_commentFragment));
        /*Triler*/
        mBinding.btnTriler.setOnClickListener(view1 -> Elements.DialogVideoPlayer(getActivity(),urlTriler));

        Uri data = getActivity().getIntent().getData();
        if (data !=null){
            String path = data.getPath();
            if (path != null && !path.isEmpty()){
                String[] parts = path.split("/");
                if (parts.length == 3){
                    String filmId = parts[2];
                    Toast.makeText(getActivity(), "ID: " + filmId, Toast.LENGTH_SHORT).show();
                }
            }
        }

        mBinding.btnReport.setOnClickListener(view1 -> ProfileFragment.sheetReport(getActivity()));

    }
    private void loadData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("DETAILS_ITEM", Context.MODE_PRIVATE);
        idUnique = sharedPreferences.getString("ID_ITEM", "0");
        requestFilm = ApiClinent.getApiClinent(getActivity(),ApiServer.urlData()).create(ApiInterFace.class);

        requestFilm.getItemByItem(idUnique).enqueue(new Callback<Film>() {
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
                    Glide.with(getActivity()).load(film.getItem_poster()).into(mBinding.itemPoster);
                    Glide.with(getActivity()).load(film.getItem_header()).into(mBinding.itemHeader);
                    typeItem= film.getItem_type();
                    if (typeItem.equals("Serial")){
                        serialModePlay(film.getItem_id());
                        mBinding.bodySerial.setVisibility(View.VISIBLE);
                    }else {
                        mBinding.bodySerial.setVisibility(View.GONE);
                    }
                    mBinding.itemPoster.setOnClickListener(view1 -> Elements.DialogPreImage(getActivity(), film.getItem_poster()));

                    mBinding.btnComment.setOnClickListener(view -> {
                        Navigation.findNavController(view).navigate(R.id.action_detailsFragment_to_commentFragment);
                    });
                    btnPlay.setOnClickListener(view1 -> qualitySheet(film.getItem_id()));
                    /*Archive*/
                    mBinding.btnArchive.setOnClickListener(view1 -> insertFavorites(film.getItem_unique() ,film.getItem_title_en(), film.getItem_country(), film.getItem_year(), film.getItem_poster()));

                    /*Chip*/
                    loadChip(film.getItem_genre());

                }else {
                    Toast.makeText(getActivity(), "پاک شده است", Toast.LENGTH_SHORT).show();
                    loader.close();
                }
            }
            @Override
            public void onFailure(Call<Film> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                loader.close();
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void loadChip(String gnre) {

        String[] genres = gnre.split(", ");
        ChipGroup chipGroup = getActivity().findViewById(R.id.chipGroup);
        for (String genre : genres) {
            Chip chip = new Chip(getActivity());
            chip.setText(genre.trim());
            chip.setClickable(true);
            chip.setCheckable(false);
            chip.setTextSize(12);
            chip.setChipBackgroundColorResource(R.color.theme_background_body);
            // تنظیم رنگ متن از R.color.themetxt
            chip.setTextColor(ContextCompat.getColor(getActivity(), R.color.theme_main));
            chipGroup.addView(chip);
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DETAILS_ITEM", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            chip.setOnClickListener(view -> {
                Navigation.findNavController(view).navigate(R.id.action_detailsFragment_to_listUniqueFragment);
                editor.putString("GROUP_TYPE", "item_genre");
                editor.putString("GROUP_NAME", genre);
                editor.putString("GROUP_VECTOR", "");
                editor.apply();
            });
        }


    }

    private void qualitySheet(String id) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.sheet_quality, null);
        BottomSheetDialog QualitySheet = new BottomSheetDialog(getActivity());
        QualitySheet.setContentView(view);
        QualitySheet.show();
        requestUrl = ApiClinent.getApiClinent(getActivity(),ApiServer.urlData()).create(ApiInterFace.class);
        recyclerUrl = view.findViewById(R.id.qualityRecycler);
        recyclerUrl.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerUrl.setLayoutManager(layoutManager);
        requestUrl.getMoviePlay(id).enqueue(new Callback<List<Movie_Play>>() {
            @Override
            public void onResponse(Call<List<Movie_Play>> call, Response<List<Movie_Play>> response) {
                listUrl = response.body();
                qualityAdapter = new QualityAdapter(getActivity().getApplicationContext(), listUrl, getActivity());
                recyclerUrl.setAdapter(qualityAdapter);
            }
            @Override
            public void onFailure(Call<List<Movie_Play>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void serialModePlay(String idSerial) {
        btnPlay.setVisibility(View.GONE);
        btnPlay.setEnabled(false);
        requestSeason = ApiClinent.getApiClinent(getActivity(),ApiServer.urlData()).create(ApiInterFace.class);
        recyclerSeason = mBinding.recyclerSeason;
        recyclerSeason.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerSeason.setLayoutManager(layoutManager);

        requestSeason.getSeason(idSerial).enqueue(new Callback<List<Season>>() {
            @Override
            public void onResponse(Call<List<Season>> call, Response<List<Season>> response) {
                Toast.makeText(getActivity(), "Suucess", Toast.LENGTH_SHORT).show();
                seasonList = response.body();
                seasonAdapter = new SeasonAdapter(getActivity().getApplicationContext(), seasonList, getActivity());
                recyclerSeason.setAdapter(seasonAdapter);
            }

            @Override
            public void onFailure(Call<List<Season>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void insertFavorites(String id ,String title, String country, String year, String poster) {
        class SaveFavorites extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                Favorites favorites = new Favorites();
                favorites.setUnique_item(id);
                favorites.setTitle_item(title);
                favorites.setCountry_item(country);
                favorites.setYear_item(year);
                favorites.setPoster_item(poster);
                DatabaseClient
                        .getInstance(getActivity()).getAppDatabase()
                        .favoritesDao()
                        .insertFavorites(favorites);
                return null;
            }
        }
        SaveFavorites saveFavorites = new SaveFavorites();
        saveFavorites.execute();

    }
}