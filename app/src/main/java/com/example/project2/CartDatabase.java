package com.example.project2;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {CartDB.class}, version = 1, exportSchema = false)
public abstract class CartDatabase extends RoomDatabase {

    public abstract CartDAO cartDao();


}
