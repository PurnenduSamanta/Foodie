package com.example.purnendu.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.purnendu.R
import com.example.purnendu.activity.CategoryMealsActivity
import com.example.purnendu.activity.MealActivity
import com.example.purnendu.adapter.CategoriesRecyclerAdapter
import com.example.purnendu.databinding.FragmentCategoriesBinding
import com.example.purnendu.databinding.FragmentHomeBinding
import com.example.purnendu.viewModel.CategoryViewModel
import com.example.purnendu.viewModel.HomeViewModel

class CategoriesFragment : Fragment() {


    private lateinit var categoryMealsAdapter: CategoriesRecyclerAdapter
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var viewModel:CategoryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(this)[CategoryViewModel::class.java]
        categoryMealsAdapter= CategoriesRecyclerAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.categoryRecyclerView.adapter=categoryMealsAdapter
        binding.categoryRecyclerView.layoutManager= GridLayoutManager(requireContext(),3)

        viewModel.getCategories()

        viewModel.observerCategoriesMealsLiveData().observe(viewLifecycleOwner, Observer {
            categoryMealsAdapter.setCategoryList(it)
        })

        categoryMealsAdapter.onItemClick={
            val intent= Intent(context, CategoryMealsActivity::class.java)
            intent.putExtra(HomeFragment.CATEGORY_NAME,it.strCategory)
            startActivity(intent)
        }

    }

}