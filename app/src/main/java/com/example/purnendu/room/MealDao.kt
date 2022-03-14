package com.example.purnendu.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface MealDao {

    @Insert
    suspend fun insert(data: MealModel)


    @Query("SELECT * FROM `Favorite Meal`")
     fun getFavouriteFood(): LiveData<List<MealModel>>


    @Query("DELETE FROM `Favorite Meal` where mealId=:mealId")
    suspend fun delete(mealId:String)

    @Query("select count(mealId) from `favorite meal` where mealId= :mealId")
    suspend fun isFavourite(mealId:String):Long
}