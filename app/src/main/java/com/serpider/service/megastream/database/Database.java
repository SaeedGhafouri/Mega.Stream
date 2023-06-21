package com.serpider.service.megastream.database;

import androidx.room.RoomDatabase;

import com.serpider.service.megastream.model.Favorites;

@androidx.room.Database(entities = {Favorites.class} , version = 1)
public abstract class Database extends RoomDatabase {
    public abstract FavoritesDao favoritesDao();
}
