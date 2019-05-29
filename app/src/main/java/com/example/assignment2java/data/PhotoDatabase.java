package com.example.assignment2java.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Photo.class}, version = 1, exportSchema = false)
public abstract class PhotoDatabase extends RoomDatabase {
    private static PhotoDatabase INSTANCE;

    public abstract PhotoDao photoDao();

    private static final Object sLock = new Object();

    public static PhotoDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        PhotoDatabase.class, "photodb")
                        .fallbackToDestructiveMigration()
                        .build();
            }

            return INSTANCE;
        }
    }
}
