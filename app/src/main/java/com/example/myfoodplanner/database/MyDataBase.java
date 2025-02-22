package com.example.myfoodplanner.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myfoodplanner.model.mealdetails.MealDetails;

@Database(entities = {MealDetails.class}, version = 2)
public abstract class MyDataBase extends RoomDatabase {
    private static MyDataBase instance = null;
    public abstract MealDetailsDao getMealDetailsDao();
    public static MyDataBase getInstance(Context context){
        if(instance == null){
            return Room.databaseBuilder(context, MyDataBase.class, "mealDetailsDb")
                    .fallbackToDestructiveMigration()
                    .build();
        }else {
            return instance;
        }

    }
}
