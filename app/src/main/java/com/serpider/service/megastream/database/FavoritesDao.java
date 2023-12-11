package com.serpider.service.megastream.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.serpider.service.megastream.model.Favorites;

import java.util.List;

@Dao
public interface FavoritesDao {

    @Query("SELECT * FROM favorites")
    List<Favorites> getAllFavorites();

    @Insert
    void insertFavorites (Favorites favoritesList) ;

    @Query("DELETE FROM favorites WHERE unique_id = :unique")
    abstract void deleteById(int unique);

    @Query("SELECT * FROM favorites WHERE unique_id = :unique LIMIT 1")
    Favorites getFavoritesById(int unique);

}
