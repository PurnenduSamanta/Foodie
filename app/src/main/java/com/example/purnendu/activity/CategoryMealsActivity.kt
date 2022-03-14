package com.example.purnendu.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.purnendu.R
import com.example.purnendu.adapter.CategoryMealsAdapter
import com.example.purnendu.databinding.ActivityCategoryMealsBinding
import com.example.purnendu.fragment.HomeFragment
import com.example.purnendu.viewModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var viewModel:CategoryMealsViewModel
    private lateinit var adapter: CategoryMealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_category_meals)
        viewModel= ViewModelProvider(this)[CategoryMealsViewModel::class.java]

        viewModel.getMealsByCategoryName(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)


        adapter=CategoryMealsAdapter()
        val layoutManager= GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
        binding.mealRecyclerview.adapter=adapter
        binding.mealRecyclerview.layoutManager=layoutManager


        viewModel.mealsLiveData.observe(this) {mealList->
            binding.tvCategoryCount.text=mealList.size.toString()
            adapter.setCategoryList(mealList)
        }

        adapter.setOnMealClickListener={
            val intent= Intent(this@CategoryMealsActivity,MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID,it.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME,it.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB,it.strMealThumb)
            startActivity(intent)
        }


    }
}