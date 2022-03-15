package com.example.purnendu.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purnendu.ResponseHandle
import com.example.purnendu.pojo.MealList
import com.example.purnendu.retrofit.RetrofitInstance
import com.example.purnendu.room.MealDatabase
import com.example.purnendu.room.MealModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealsViewModel(private val database: MealDatabase) : ViewModel() {

    private var mealLiveData: MutableLiveData<ResponseHandle<MealList.Meal>> = MutableLiveData()

    fun getMealDetail(id: String) {
        mealLiveData.postValue(ResponseHandle.Loading())
        RetrofitInstance.foodApi.getMealById(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    if (response.isSuccessful)
                        mealLiveData.postValue(ResponseHandle.Success(response.body()?.meals?.get(0)))
                } else
                    return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                mealLiveData.postValue(ResponseHandle.Error(t.message.toString()))
            }

        })
    }


    fun insertMealToFavourite(mealId: String, mealName: String, mealThumb: String) {
        viewModelScope.launch(Dispatchers.IO) {
            database.MealDao().insert(MealModel(mealId, mealName, mealThumb))
        }
    }

    fun observeMealLiveData(): LiveData<ResponseHandle<MealList.Meal>> {
        return mealLiveData
    }

    suspend fun isFavouriteMeal(mealId: String): Boolean {
        var count: Long = 0
        viewModelScope.launch(Dispatchers.IO) {
            count = database.MealDao().isFavourite(mealId)
        }.join()
        return count != 0L
    }

    fun deleteFavouriteMeal(mealId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            database.MealDao().delete(mealId)
        }


    }


}