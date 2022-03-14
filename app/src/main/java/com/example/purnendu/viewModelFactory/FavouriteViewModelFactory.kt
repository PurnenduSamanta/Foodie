package com.example.purnendu.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.purnendu.room.MealDatabase
import com.example.purnendu.viewModel.FavouriteViewModel

class FavouriteViewModelFactory(private val database: MealDatabase) :ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavouriteViewModel(database) as T
    }
}