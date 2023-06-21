package com.serpider.service.megastream.database;
import android.content.Context;
import androidx.room.Room;

public class DatabaseClient {
    private final Context context;
    private static DatabaseClient mInstance;
    private final Database database;

    private DatabaseClient(Context mCtx) {
        this.context = mCtx;
        database = Room.databaseBuilder(mCtx, Database.class, "favorites").build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx){
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public Database getAppDatabase() {
        return database;
    }

}
