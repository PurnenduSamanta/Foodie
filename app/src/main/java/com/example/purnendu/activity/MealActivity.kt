package com.example.purnendu.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.purnendu.R
import com.example.purnendu.ResponseHandle
import com.example.purnendu.databinding.ActivityMealBinding
import com.example.purnendu.fragment.HomeFragment
import com.example.purnendu.room.MealDatabase
import com.example.purnendu.viewModel.MealsViewModel
import com.example.purnendu.viewModelFactory.MealsViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private lateinit var mealViewModel: MealsViewModel
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var youTubeLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meal)

        val database = MealDatabase.getDataBase(this)
        mealViewModel =
            ViewModelProvider(this, MealsViewModelFactory(database))[MealsViewModel::class.java]

        getMealInfoFromIntent()
        setUpViewWithMealInformation()


        mealViewModel.getMealDetail(mealId)
        observeMealLiveData()


        binding.imgYoutube.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youTubeLink))
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    this@MealActivity,
                    "No Application found to perform ",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }


        binding.btnSave.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                if (checkAlreadyFavourite()) {
                    mealViewModel.deleteFavouriteMeal(mealId)
                    binding.btnSave.setImageResource(R.drawable.ic_baseline_save_24)
                } else {
                    mealViewModel.insertMealToFavourite(mealId, mealName, mealThumb)
                    binding.btnSave.setImageResource(R.drawable.ic_baseline_done_24)
                }

            }

        }

        lifecycleScope.launch(Dispatchers.IO) {
            if (checkAlreadyFavourite())
                binding.btnSave.setImageResource(R.drawable.ic_baseline_done_24)
        }


    }

    private fun observeMealLiveData() {
        mealViewModel.observeMealLiveData().observe(this) {

            when (it) {
                is ResponseHandle.Loading -> {
                    loadingCase()
                }
                is ResponseHandle.Success -> {
                    onResponseCase()
                    binding.tvCategoryInfo.text = "Category: ${it.data?.strCategory}"
                    binding.tvAreaInfo.text = "Area: ${it.data?.strArea}"
                    binding.tvContent.text = it.data?.strInstructions
                    youTubeLink = it.data?.strYoutube.toString()

                }
                is ResponseHandle.Error -> {
                    Toast.makeText(this@MealActivity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setUpViewWithMealInformation() {
        binding.apply {
            collapsingToolbar.title = mealName
            Glide.with(this@MealActivity)
                .load(mealThumb)
                .into(imgMealDetail)

            collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE)
            collapsingToolbar.setExpandedTitleColor(Color.WHITE)
        }

    }

    private fun getMealInfoFromIntent() {
        val tempIntent = intent
        this.mealId = tempIntent.getStringExtra(HomeFragment.MEAL_ID)!!
        this.mealName = tempIntent.getStringExtra(HomeFragment.MEAL_NAME)!!
        this.mealThumb = tempIntent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnSave.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvAreaInfo.visibility = View.INVISIBLE
        binding.tvCategoryInfo.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnSave.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvAreaInfo.visibility = View.VISIBLE
        binding.tvCategoryInfo.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE

    }

    private suspend fun checkAlreadyFavourite(): Boolean {
        var isAlreadyAdded = false
        lifecycleScope.launch(Dispatchers.IO) {
            isAlreadyAdded = mealViewModel.isFavouriteMeal(mealId)
        }.join()
        return isAlreadyAdded
    }


}