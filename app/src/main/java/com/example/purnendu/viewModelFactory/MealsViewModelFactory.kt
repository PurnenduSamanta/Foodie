package com.example.purnendu.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.purnendu.room.MealDatabase
import com.example.purnendu.viewModel.MealsViewModel

class MealsViewModelFactory(private val database: MealDatabase) :ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealsViewModel(database) as T
    }


}