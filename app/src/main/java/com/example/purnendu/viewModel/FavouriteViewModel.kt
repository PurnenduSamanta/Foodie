package com.example.purnendu.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.purnendu.room.MealDatabase
import com.example.purnendu.room.MealModel

class FavouriteViewModel(private val database: MealDatabase) : ViewModel() {


    val mutableFavouriteMealsLiveData: LiveData<List<MealModel>>
        get() = database.MealDao().getFavouriteFood()


}