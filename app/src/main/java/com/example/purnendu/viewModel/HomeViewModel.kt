package com.example.purnendu.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfood.data.pojo.Category
import com.example.easyfood.data.pojo.CategoryList
import com.example.purnendu.pojo.MealsByCategoryList
import com.example.purnendu.pojo.MealList
import com.example.purnendu.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private var randomLiveData = MutableLiveData<MealList.Meal>()
    private var popularMealsLiveData = MutableLiveData<MealsByCategoryList>()
    private var categoriesMealsLiveData = MutableLiveData<List<Category>>()


    fun getRandomMeal() {
        RetrofitInstance.foodApi.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: MealList.Meal = response.body()!!.meals[0]
                    randomLiveData.postValue(randomMeal)
                } else
                    return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {

            }

        })
    }


    fun getPopularMeals() {
        RetrofitInstance.foodApi.getPopularMeals("Seafood")
            .enqueue(object : Callback<MealsByCategoryList> {
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {
                    if (response.body() != null) {
                        popularMealsLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {

                }


            })
    }

    fun getCategories() {
        RetrofitInstance.foodApi.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let {
                    categoriesMealsLiveData.postValue(it.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {

            }
        })
    }

    fun observeRandomMealLiveData(): LiveData<MealList.Meal> {
        return randomLiveData
    }

    fun observerPopularMealsLiveData(): LiveData<MealsByCategoryList> {
        return popularMealsLiveData
    }

    fun observerCategoriesMealsLiveData(): LiveData<List<Category>> {
        return categoriesMealsLiveData
    }


}