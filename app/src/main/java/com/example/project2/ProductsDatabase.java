package com.example.project2;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {ProductsDB.class}, version = 1, exportSchema = false)
public abstract class ProductsDatabase extends RoomDatabase {

    public abstract ProductsDAO productsDao();

}
