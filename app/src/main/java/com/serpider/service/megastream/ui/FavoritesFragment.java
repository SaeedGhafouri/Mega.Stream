package com.serpider.service.megastream.ui;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.serpider.service.megastream.adapter.FavoriteAdapter;
import com.serpider.service.megastream.database.DatabaseClient;
import com.serpider.service.megastream.databinding.FragmentFavoritesBinding;
import com.serpider.service.megastream.model.Favorites;

import java.util.List;


public class FavoritesFragment extends Fragment {

    FragmentFavoritesBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentFavoritesBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getFavoritesList();
        mBinding.recyclerFavorite.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        mBinding.recyclerFavorite.setLayoutManager(layoutManager);

    }

    private void getFavoritesList() {
        class GetTasks extends AsyncTask<Void, Void, List<Favorites>> {
            @Override
            protected List<Favorites> doInBackground(Void... voids) {
                List<Favorites> favoritesList = DatabaseClient
                        .getInstance(getActivity())
                        .getAppDatabase()
                        .favoritesDao()
                        .getAllFavorites();
                return favoritesList;
            }

            @Override
            protected void onPostExecute(List<Favorites> favoritesList) {
                super.onPostExecute(favoritesList);
                FavoriteAdapter adapter = new FavoriteAdapter(getActivity(), getActivity(), favoritesList);

                mBinding.recyclerFavorite.setAdapter(adapter);
            }
        }
        GetTasks gt = new GetTasks();
        gt.execute();

    }
}