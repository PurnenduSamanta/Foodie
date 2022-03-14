package com.example.purnendu.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MealModel::class], version = 1, exportSchema = false)

abstract class MealDatabase : RoomDatabase() {

    abstract fun MealDao(): MealDao

    companion object {
        @Volatile
        private var INSTANCE: MealDatabase? = null

        fun getDataBase(context: Context): MealDatabase {
            if (INSTANCE == null) {
                synchronized(this)
                {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MealDatabase::class.java,
                        "MealDb"
                    ).build()
                }
            }
            return INSTANCE!!

        }
    }


}