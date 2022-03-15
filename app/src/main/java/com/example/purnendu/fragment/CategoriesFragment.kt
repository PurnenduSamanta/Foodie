package com.example.purnendu.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.purnendu.ResponseHandle
import com.example.purnendu.activity.CategoryMealsActivity
import com.example.purnendu.adapter.CategoriesRecyclerAdapter
import com.example.purnendu.databinding.FragmentCategoriesBinding
import com.example.purnendu.viewModel.CategoryViewModel

class CategoriesFragment : Fragment() {


    private lateinit var categoryMealsAdapter: CategoriesRecyclerAdapter
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var viewModel:CategoryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(this)[CategoryViewModel::class.java]
        categoryMealsAdapter= CategoriesRecyclerAdapter(requireContext())

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

        viewModel.observerCategoriesMealsLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is ResponseHandle.Loading -> {
                    binding.categoryRecyclerView.visibility = View.INVISIBLE
                    binding.loadingGif.visibility = View.VISIBLE
                }
                is ResponseHandle.Success -> {
                    binding.categoryRecyclerView.visibility = View.VISIBLE
                    binding.loadingGif.visibility = View.GONE
                    it.data?.let { it1 -> categoryMealsAdapter.setCategoryList(it1) }
                }
                is ResponseHandle.Error -> {
                    binding.loadingGif.visibility = View.GONE
                    binding.categoryRecyclerView.visibility = View.INVISIBLE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }


        }

        categoryMealsAdapter.onItemClick={
            val intent= Intent(context, CategoryMealsActivity::class.java)
            intent.putExtra(HomeFragment.CATEGORY_NAME,it.strCategory)
            startActivity(intent)
        }

    }

}