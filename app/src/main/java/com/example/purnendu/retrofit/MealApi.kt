package com.example.purnendu.retrofit

import com.example.easyfood.data.pojo.CategoryList
import com.example.purnendu.pojo.MealsByCategoryList
import com.example.purnendu.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET ("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php?")
    fun getMealById(@Query("i") id:String):Call<MealList>

    @GET("search.php?")
    fun getMealByName(@Query("s") s:String):Call<MealList>

    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    @GET("filter.php?")
    fun getPopularMeals(@Query("c") category:String):Call<MealsByCategoryList>
}