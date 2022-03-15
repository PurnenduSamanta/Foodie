package com.example.purnendu.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.purnendu.NetworkUtility
import com.example.purnendu.ResponseHandle
import com.example.purnendu.pojo.Category
import com.example.purnendu.pojo.CategoryList
import com.example.purnendu.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel(application: Application) : AndroidViewModel(application) {


    private var categoriesMealsLiveData = MutableLiveData<ResponseHandle<List<Category>>>()


    fun getCategories() {
        if (!NetworkUtility.checkConnection(getApplication())) {
            categoriesMealsLiveData.postValue(ResponseHandle.Error("No connection"))
            return
        }
        categoriesMealsLiveData.postValue(ResponseHandle.Loading())
        RetrofitInstance.foodApi.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {

                if (response.body() == null)
                    return
                if (!response.isSuccessful)
                    return
                categoriesMealsLiveData.postValue(ResponseHandle.Success(response.body()?.categories))
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                categoriesMealsLiveData.postValue(ResponseHandle.Error(t.message.toString()))
            }
        })
    }


    fun observerCategoriesMealsLiveData(): LiveData<ResponseHandle<List<Category>>> {
        return categoriesMealsLiveData
    }


}