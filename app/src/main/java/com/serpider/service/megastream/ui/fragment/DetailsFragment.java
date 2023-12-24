package com.serpider.service.megastream.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
import androidx.room.Room;

import android.os.Handler;
import android.util.Log;
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
import com.serpider.service.megastream.adapter.CommentAdapter;
import com.serpider.service.megastream.adapter.QualityAdapter;
import com.serpider.service.megastream.adapter.SeasonAdapter;
import com.serpider.service.megastream.database.DatabaseClient;
import com.serpider.service.megastream.database.FavoritesDao;
import com.serpider.service.megastream.interfaces.Key;
import com.serpider.service.megastream.model.Comment;
import com.serpider.service.megastream.model.Favorites;
import com.serpider.service.megastream.model.PlayUrl;
import com.serpider.service.megastream.model.Season;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.databinding.FragmentDetailsBinding;
import com.serpider.service.megastream.interfaces.Elements;
import com.serpider.service.megastream.model.Film;
import com.serpider.service.megastream.ui.activity.PlayerActivity;
import com.serpider.service.megastream.util.LoaderFullScreen;
import com.serpider.service.megastream.util.ReportSheet;
import com.serpider.service.megastream.util.SnackBoard;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsFragment extends Fragment {
    private ApiInterFace requestFilm, requestSeason;
    private String urlTriler;
    private int typeItem, idUnique;
    private SeasonAdapter seasonAdapter;
    private ApiInterFace requestUrl ;
    private QualityAdapter qualityAdapter;
    private List<PlayUrl> listUrl = new ArrayList<>();
    private List<Season> seasonList = new ArrayList<>();
    RecyclerView recyclerUrl, recyclerSeason;
    MaterialButton btnPlay;
    private FragmentDetailsBinding mBinding;
    private CommentAdapter commentAdapter;
    private boolean isFavorite;
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
        mBinding.btnBack.setOnClickListener(view1 -> getActivity().onBackPressed());

        btnPlay = mBinding.btnPlay;

        try {
            loadData();
        }catch (Exception e){
            getActivity().onBackPressed();
        }

        /*Test Code*/
        /*Deep link*/
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

    }
    private void loadData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("DETAILS_ITEM", Context.MODE_PRIVATE);
        idUnique = sharedPreferences.getInt("ID_ITEM", 0);
        requestFilm = ApiClinent.getApiClinent(getActivity(), Key.BASE_URL).create(ApiInterFace.class);

        /*comment*/
        mBinding.recyclerComment.setVisibility(View.VISIBLE);
        mBinding.recyclerComment.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        mBinding.recyclerComment.setLayoutManager(layoutManager);
        requestFilm.getFilmDetails(idUnique).enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                Film film = response.body();
                new Handler().postDelayed(() -> {
                    mBinding.loader.setVisibility(View.GONE);
                    mBinding.bodyDetails.setVisibility(View.VISIBLE);
                },1000);
                if(film != null){
                    mBinding.itemTitle.setText(film.getTitle_en());
                    mBinding.itemTitleFa.setText(film.getTitle_fa());
                    mBinding.itemImdb.setText(String.valueOf(film.getImdb()));
                    mBinding.itemSynopsis.setText(film.getSynopsis());
                    mBinding.itemLanguageAlpha.setText(film.getLanguage_alpha());
                    urlTriler= film.getTrailer();
                    Glide.with(getActivity()).load(film.getPoster()).into(mBinding.itemPoster);

                    /*Header @s*/
                    if (film.getHeader().isEmpty()) {
                        mBinding.itemHeader.setVisibility(View.GONE);
                    }else {
                        Glide.with(getActivity()).load(film.getHeader()).into(mBinding.itemHeader);
                    }
                    /*Header @e*/

                    /*Awards @s*/
                    if (film.getSuggestion() == 0){
                        mBinding.bodyAward.setVisibility(View.GONE);
                        mBinding.lineAward.setVisibility(View.GONE);
                    }else {

                    }
                    /*Awards @e*/

                    /*Desc @e*/
                    if (film.getDesc().isEmpty()){
                        mBinding.bodyDesc.setVisibility(View.GONE);
                    }else {
                        mBinding.itemDesc.setText(String.valueOf(film.getDesc()));
                    }
                    /*Desc @e*/

                    /*Age @s*/
                    if (film.getAges().isEmpty()) {
                        mBinding.bodyAge.setVisibility(View.GONE);
                        mBinding.lineAge.setVisibility(View.GONE);
                    }else {
                        mBinding.itemAge.setText(film.getAges());
                    }
                    /*Age @e*/

                    /*Year @s*/
                    if (film.getYear() == 0) {
                        mBinding.bodyYear.setVisibility(View.GONE);
                        mBinding.lineYear.setVisibility(View.GONE);
                    }else {
                        mBinding.itemYear.setText(String.valueOf(film.getYear()));
                    }
                    /*Year @e*/

                    /*Country @s*/
                    if (film.getCountry().isEmpty()){
                        mBinding.bodyCountry.setVisibility(View.GONE);
                        mBinding.lineCountry.setVisibility(View.GONE);
                    }else {
                        mBinding.itemCountry.setText(film.getCountry());
                    }
                    /*Country @e*/

                    /*Lan @e*/
                    if (film.getLanguage_alpha().isEmpty()){
                        mBinding.bodyLanguage.setVisibility(View.GONE);
                        mBinding.lineLanguage.setVisibility(View.GONE);
                    }else {
                        mBinding.itemLanguage.setText(film.getLanguage());
                    }
                    /*Lan @e*/

                    /*Size @e*/
                    if (film.getSize() == 0){
                        mBinding.bodySize.setVisibility(View.GONE);
                        mBinding.lineSize.setVisibility(View.GONE);
                    }else {
                        mBinding.itemSize.setText(String.valueOf(film.getSize()));
                    }
                    /*Size @e*/

                    /*Network @s*/
                    if (film.getNetwork() == null){
                        mBinding.bodyNetwork.setVisibility(View.GONE);
                    }else {
                        mBinding.itemNetwork.setText(film.getNetwork());
                        Glide.with(getActivity()).load(film.getNetwork_vector()).into(mBinding.itemNetworkVector);
                    }
                    /*Network @e*/

                    Glide.with(getActivity()).load(film.getCountry_flag()).into(mBinding.itemCountryFlag);
                    typeItem= film.getType();
                    if (typeItem == 2){
                        serialModePlay(film.getId());
                        mBinding.bodySerial.setVisibility(View.VISIBLE);
                    }else {
                        mBinding.bodySerial.setVisibility(View.GONE);
                    }
                    if (film.getComment_count() > 0) {
                        mBinding.txtCommentCount.setText(String.valueOf(film.getComment_count()));
                    }else {
                        mBinding.txtCommentCount.setVisibility(View.GONE);
                    }
                    mBinding.itemPoster.setOnClickListener(view1 -> Elements.DialogPreImage(getActivity(), film.getPoster()));

                    /*limit comment@s*/
                    if (film.getListComment().size() > 0) {
                        commentAdapter = new CommentAdapter(getActivity(), film.getListComment());
                        mBinding.recyclerComment.setAdapter(commentAdapter);
                    }
                    /*limit comment@e*/

                    btnPlay = getActivity().findViewById(R.id.btnPlay);
                    mBinding.btnTriler.setOnClickListener(view1 -> {
                        Intent intent = new Intent(getActivity(), PlayerActivity.class);
                        intent.putExtra("URL_PLAY", film.getTrailer());
                        intent.putExtra("URL_TITLE", "Preview " + film.getTitle_en());
                        getActivity().startActivity(intent);
                    });
                    /*Triler @e*/
                    if (film.getTrailer().isEmpty()){
                        mBinding.bodyTriler.setVisibility(View.GONE);
                        mBinding.lineTriler.setVisibility(View.GONE);
                    }else {
                        mBinding.itemSize.setText(String.valueOf(film.getSize()));
                    }
                    /*Triler @e*/

                    /*Comment*/
                    mBinding.btnComment.setOnClickListener(view -> {
                        Navigation.findNavController(view).navigate(R.id.action_detailsFragment_to_commentFragment);
                    });
                    isItemInFavorites(film.getId());
                    /*Archive*/
                    mBinding.btnArchive.setOnClickListener(view1 -> {
                        if (isFavorite) {
                            mBinding.btnArchive.setImageResource(R.drawable.ic_star);
                            deleteItem(film.getId());
                            isFavorite=false;
                        } else {
                            mBinding.btnArchive.setImageResource(R.drawable.ic_star_bold);
                            insertFavorites(film.getId(), film.getTitle_en(), film.getCountry(), film.getYear(), film.getPoster());
                            isFavorite=true;
                        }
                    });

                    /*Share*/
                    mBinding.btnShare.setOnClickListener(view -> share(film.getId()));
                    /*Play*/
                    btnPlay.setOnClickListener(view1 -> qualitySheet(film.getId(), film.getTitle_en()));
                    /*if (film.getDesc().isEmpty()){
                        mBinding.titleDesc.setVisibility(View.GONE);
                    }*/

                    /*Chip*/
                    loadChip(film.getGenre());

                    /*Click item*/
                    //award
                    mBinding.bodyAward.setOnClickListener(view -> {
                        clickItem(R.id.action_detailsFragment_to_listUniqueFragment, "item_suggestion", String.valueOf(film.getSuggestion()), "پیشنهاد سردبیر", "");
                    });
                    //Ages
                    mBinding.bodyAge.setOnClickListener(view -> {
                        clickItem(R.id.action_detailsFragment_to_listUniqueFragment, "item_ages", film.getAges(), "محدوده سنی " + film.getAges(), "");
                    });
                    //Year
                    mBinding.bodyYear.setOnClickListener(view -> {
                        clickItem(R.id.action_detailsFragment_to_listUniqueFragment, "item_yaer", String.valueOf(film.getYear()), String.valueOf(film.getYear()), "");
                    });
                    //Conutry
                    mBinding.bodyCountry.setOnClickListener(view -> {
                        clickItem(R.id.action_detailsFragment_to_listUniqueFragment, "item_country", film.getCountry(), film.getCountry(), film.getCountry_flag());
                    });
                    //Network
                    mBinding.bodyNetwork.setOnClickListener(view -> {
                        clickItem(R.id.action_detailsFragment_to_listUniqueFragment, "item_network", film.getNetwork(), film.getNetwork(), film.getNetwork_vector());
                    });

                    mBinding.btnReport.setOnClickListener(view1 -> {
                        ReportSheet reportSheet = new ReportSheet();
                        reportSheet.show(getChildFragmentManager(), reportSheet.getTag());
                    });

                }else {
                    SnackBoard.show(getActivity(), "آیتم مورد نظر حذف شده است", 1);
                    getActivity().onBackPressed();
                }
            }
            @Override
            public void onFailure(Call<Film> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clickItem(int action, String query, String name, String title, String vector) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DETAILS_ITEM", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Navigation.findNavController(getView()).navigate(action);
        editor.putString("GROUP_QUERY", query);
        editor.putString("GROUP_NAME", name);
        editor.putString("GROUP_TITLE", title);
        editor.putString("GROUP_VECTOR", vector);
        editor.apply();
    }

    private void share(int id) {
        String urlItem = "https://pluslux.xyz/" + id;
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,  "سلام لینک: "+ urlItem);
        sendIntent.setType("text/plain");
        getActivity().startActivity(sendIntent);
    }

    @SuppressLint("ResourceAsColor")
    private void loadChip(String gnre) {
        String[] genres = gnre.split(", ");
        ChipGroup chipGroup = getActivity().findViewById(R.id.chipGroup);
        for (String genre : genres) {
            Chip chip = new Chip(getActivity());
            chip.setText(genre);
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
                editor.putString("GROUP_QUERY", "item_genre");
                editor.putString("GROUP_NAME", genre);
                editor.putString("GROUP_VECTOR", "");
                editor.apply();
            });
        }
    }

    private void qualitySheet(int id, String title) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.sheet_quality, null);
        BottomSheetDialog QualitySheet = new BottomSheetDialog(getActivity());
        QualitySheet.setContentView(view);
        QualitySheet.show();
        requestUrl = ApiClinent.getApiClinent(getActivity(),Key.BASE_URL).create(ApiInterFace.class);
        recyclerUrl = view.findViewById(R.id.qualityRecycler);
        recyclerUrl.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerUrl.setLayoutManager(layoutManager);
        requestUrl.getMovieUrl(id).enqueue(new Callback<List<PlayUrl>>() {
            @Override
            public void onResponse(Call<List<PlayUrl>> call, Response<List<PlayUrl>> response) {
                listUrl = response.body();
                qualityAdapter = new QualityAdapter(getActivity().getApplicationContext(), listUrl, getActivity(), title);
                recyclerUrl.setAdapter(qualityAdapter);
            }
            @Override
            public void onFailure(Call<List<PlayUrl>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void serialModePlay(int idSerial) {
        btnPlay.setVisibility(View.GONE);
        btnPlay.setEnabled(false);
        requestSeason = ApiClinent.getApiClinent(getActivity(),Key.BASE_URL).create(ApiInterFace.class);
        recyclerSeason = mBinding.recyclerSeason;
        recyclerSeason.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerSeason.setLayoutManager(layoutManager);

        requestSeason.getSerialSeason(idSerial).enqueue(new Callback<List<Season>>() {
            @Override
            public void onResponse(Call<List<Season>> call, Response<List<Season>> response) {
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
    private void insertFavorites(int id ,String title, String country, int year, String poster) {
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

    private boolean isItemInFavorites(int id) {
        class CheckFavorites extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected Boolean doInBackground(Void... voids) {
                // انجام کوئری برای چک کردن وجود آیتم با ایدی مشخص
                FavoritesDao favoritesDao = DatabaseClient.getInstance(getActivity()).getAppDatabase().favoritesDao();
                Favorites existingItem = favoritesDao.getFavoritesById(id);
                return existingItem != null;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    isFavorite = true;
                    mBinding.btnArchive.setImageResource(R.drawable.ic_star_bold);
                } else {
                    isFavorite = false;
                    mBinding.btnArchive.setImageResource(R.drawable.ic_star);
                }
            }
        }

        CheckFavorites checkFavorites = new CheckFavorites();
        checkFavorites.execute();
        return false;
    }

    private void deleteItem(int unique) {
        class DeleteFavorites extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                Favorites favorites = new Favorites();
                DatabaseClient
                        .getInstance(getActivity()).getAppDatabase()
                        .favoritesDao()
                        .deleteById(unique);

                return null;
            }
        }
        DeleteFavorites deleteFavorites = new DeleteFavorites();
        deleteFavorites.execute();

    }

}