package com.example.purnendu.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfood.data.pojo.Category
import com.example.easyfood.data.pojo.CategoryList
import com.example.purnendu.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel :ViewModel() {


    private var categoriesMealsLiveData = MutableLiveData<List<Category>>()


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


    fun observerCategoriesMealsLiveData(): LiveData<List<Category>> {
        return categoriesMealsLiveData
    }




}