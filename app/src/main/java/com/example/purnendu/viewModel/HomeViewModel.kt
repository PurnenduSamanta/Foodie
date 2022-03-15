package com.example.purnendu.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.purnendu.NetworkUtility
import com.example.purnendu.pojo.Category
import com.example.purnendu.pojo.CategoryList
import com.example.purnendu.pojo.MealsByCategoryList
import com.example.purnendu.pojo.MealList
import com.example.purnendu.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.purnendu.ResponseHandle

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var randomLiveData = MutableLiveData<ResponseHandle<MealList.Meal>>()
    private var popularMealsLiveData = MutableLiveData<ResponseHandle<MealsByCategoryList>>()
    private var categoriesMealsLiveData = MutableLiveData<ResponseHandle<List<Category>>>()


    fun getRandomMeal() {
        if (!NetworkUtility.checkConnection(getApplication())) {
            randomLiveData.postValue(ResponseHandle.Error("No Connection"))
            return
        }
        randomLiveData.postValue(ResponseHandle.Loading())
        RetrofitInstance.foodApi.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    if (response.isSuccessful) {
                        val randomMeal: MealList.Meal = response.body()!!.meals[0]
                        randomLiveData.postValue(ResponseHandle.Success(randomMeal))
                    }

                } else
                    return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                randomLiveData.postValue(ResponseHandle.Error(t.message.toString()))
            }

        })
    }


    fun getPopularMeals() {
        if (!NetworkUtility.checkConnection(getApplication())) {
            popularMealsLiveData.postValue(ResponseHandle.Error("No Connection"))
            return
        }
        popularMealsLiveData.postValue(ResponseHandle.Loading())
        RetrofitInstance.foodApi.getPopularMeals("Seafood")
            .enqueue(object : Callback<MealsByCategoryList> {
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {
                    if (response.body() != null) {
                        if (response.isSuccessful)
                            popularMealsLiveData.postValue(ResponseHandle.Success(response.body()))
                    }
                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    popularMealsLiveData.postValue(ResponseHandle.Error(t.message.toString()))
                }


            })
    }

    fun getCategories() {
        if (!NetworkUtility.checkConnection(getApplication())) {
            categoriesMealsLiveData.postValue(ResponseHandle.Error("No Connection"))
            return
        }
        categoriesMealsLiveData.postValue(ResponseHandle.Loading())
        RetrofitInstance.foodApi.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {

                if (response.body() == null)
                    return
                if (!response.isSuccessful)
                    return

                categoriesMealsLiveData.postValue(ResponseHandle.Success(response.body()!!.categories))
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                categoriesMealsLiveData.postValue(ResponseHandle.Error(t.message.toString()))
            }
        })
    }

    fun observeRandomMealLiveData(): LiveData<ResponseHandle<MealList.Meal>> {
        return randomLiveData
    }

    fun observerPopularMealsLiveData(): LiveData<ResponseHandle<MealsByCategoryList>> {
        return popularMealsLiveData
    }

    fun observerCategoriesMealsLiveData(): LiveData<ResponseHandle<List<Category>>> {
        return categoriesMealsLiveData
    }


}