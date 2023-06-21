package com.serpider.service.megastream.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.serpider.service.megastream.model.Favorites;

import java.util.List;

@Dao
public interface FavoritesDao {

    @Query("sELECT * FROM favorites")
    List<Favorites> getAllFavorites();

    @Insert
    void insertFavorites (Favorites favoritesList) ;

    @Query("DELETE FROM favorites WHERE unique_id = :unique")
    abstract void deleteById(String unique);

}
