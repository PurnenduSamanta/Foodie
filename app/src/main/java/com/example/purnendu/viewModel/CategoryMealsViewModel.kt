package com.example.purnendu.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.purnendu.pojo.MealsByCategoryList
import com.example.purnendu.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel : ViewModel() {

    val mealsLiveData = MutableLiveData<List<MealsByCategoryList.Meal>>()


    fun getMealsByCategoryName(name: String) {
        RetrofitInstance.foodApi.getPopularMeals(name)
            .enqueue(object : Callback<MealsByCategoryList> {

                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {
                    response.body()?.let {
                        mealsLiveData.postValue(it.meals)
                    }


                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {

                }
            })


    }


    fun observeMealsLiveData() : LiveData<List<MealsByCategoryList.Meal>>
    {
            return mealsLiveData
    }
}