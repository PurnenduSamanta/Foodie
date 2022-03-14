package com.example.purnendu.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.purnendu.R
import com.example.purnendu.activity.CategoryMealsActivity
import com.example.purnendu.activity.MealActivity
import com.example.purnendu.adapter.CategoriesRecyclerAdapter
import com.example.purnendu.adapter.MostPopularAdapter
import com.example.purnendu.databinding.FragmentHomeBinding
import com.example.purnendu.pojo.MealList
import com.example.purnendu.viewModel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var popularMealsAdapter:MostPopularAdapter
    private lateinit var categoryMealsAdapter:CategoriesRecyclerAdapter
    private lateinit var temp:MealList.Meal


    companion object{
        const val MEAL_ID="com.example.purnendu.fragment_id"
        const val MEAL_NAME="com.example.purnendu.fragment_name"
        const val MEAL_THUMB="com.example.purnendu.fragment_thumb"
        const val CATEGORY_NAME="com.example.purnendu.fragment_categoryName"

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(this).get(HomeViewModel::class.java)
        popularMealsAdapter=MostPopularAdapter()
        categoryMealsAdapter= CategoriesRecyclerAdapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoadingCase()
        createMostPopularMealsRecyclerView()
        createCategoryMealsRecyclerView()
        viewModel.getRandomMeal()
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner, Observer {
            this.temp=it
            Glide.with(this@HomeFragment)
                .load(it.strMealThumb)
                .into(binding.imgRandomMeal)
        })

        viewModel.getPopularMeals()
        viewModel.observerPopularMealsLiveData().observe(viewLifecycleOwner) {

            popularMealsAdapter.setAdapter(it.meals)
            binding.recViewMealsPopular.adapter = popularMealsAdapter
            popularMealsAdapter.notifyDataSetChanged()

        }

        viewModel.getCategories()
        viewModel.observerCategoriesMealsLiveData().observe(viewLifecycleOwner, Observer {

           categoryMealsAdapter.setCategoryList(it)
            binding.recyclerView.adapter = categoryMealsAdapter
            cancelLoadingCase()

        })


        binding.imgRandomMeal.setOnClickListener {
            val intent= Intent(context,MealActivity::class.java)
            intent.putExtra(MEAL_NAME,temp.strMeal)
            intent.putExtra(MEAL_ID,temp.idMeal)
            intent.putExtra(MEAL_THUMB,temp.strMealThumb)
            startActivity(intent)
        }


       popularMealsAdapter.onItemClick={
           val intent= Intent(context,MealActivity::class.java)
           intent.putExtra(MEAL_NAME,it.strMeal)
           intent.putExtra(MEAL_ID,it.idMeal)
           intent.putExtra(MEAL_THUMB,it.strMealThumb)
           startActivity(intent)
       }

        categoryMealsAdapter.onItemClick={
            val intent= Intent(context, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,it.strCategory)
            startActivity(intent)
        }


    }

    private fun createMostPopularMealsRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

        private fun createCategoryMealsRecyclerView() {
            binding.recyclerView.apply {
                layoutManager=GridLayoutManager(requireContext(),3)
            }
    }


    private fun showLoadingCase() {
        binding.apply {
            header.visibility = View.INVISIBLE

            tvWouldLikeToEat.visibility = View.INVISIBLE
            randomMeal.visibility = View.INVISIBLE
            tvOverPupItems.visibility = View.INVISIBLE
            recViewMealsPopular.visibility = View.INVISIBLE
            tvCategory.visibility = View.INVISIBLE
            categoryCard.visibility = View.INVISIBLE
            loadingGif.visibility = View.VISIBLE
            rootHome.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.g_loading))

        }
    }

    private fun cancelLoadingCase() {
        binding.apply {
            header.visibility = View.VISIBLE
            tvWouldLikeToEat.visibility = View.VISIBLE
            randomMeal.visibility = View.VISIBLE
            tvOverPupItems.visibility = View.VISIBLE
            recViewMealsPopular.visibility = View.VISIBLE
            tvCategory.visibility = View.VISIBLE
            categoryCard.visibility = View.VISIBLE
            loadingGif.visibility = View.INVISIBLE
            rootHome.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))

        }
    }


}