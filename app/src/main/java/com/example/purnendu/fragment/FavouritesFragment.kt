package com.example.purnendu.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.purnendu.activity.MealActivity
import com.example.purnendu.adapter.FavouriteMealRecyclerAdapter
import com.example.purnendu.databinding.FragmentFavouritesBinding
import com.example.purnendu.room.MealDatabase
import com.example.purnendu.viewModel.FavouriteViewModel
import com.example.purnendu.viewModelFactory.FavouriteViewModelFactory

class FavouritesFragment : Fragment() {

    private lateinit var adapter: FavouriteMealRecyclerAdapter
    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var viewModel: FavouriteViewModel
    private lateinit var database: MealDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = FavouriteMealRecyclerAdapter()

        database = context?.let { MealDatabase.getDataBase(it) }!!

        viewModel = ViewModelProvider(
            this,
            FavouriteViewModelFactory(database)
        )[FavouriteViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        binding.favRecView.adapter = adapter
        binding.favRecView.layoutManager = layoutManager

        viewModel.mutableFavouriteMealsLiveData.observe(requireActivity()) {
            if (it.isEmpty())
                binding.tvFavEmpty.visibility = View.VISIBLE
            else
                binding.tvFavEmpty.visibility = View.GONE
            adapter.setFavouriteList(it)

        }


        adapter.onItemSelected = {
            val intent = Intent(context, MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID, it.mealId)
            intent.putExtra(HomeFragment.MEAL_NAME, it.mealName)
            intent.putExtra(HomeFragment.MEAL_THUMB, it.mealThumb)
            startActivity(intent)
        }


    }


}

