package com.example.purnendu.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.purnendu.NetworkUtility
import com.example.purnendu.pojo.Category
import com.example.purnendu.databinding.CategoryItemBinding

class CategoriesRecyclerAdapter(private val context: Context) : RecyclerView.Adapter<CategoriesRecyclerAdapter.CategoryViewHolder>() {
    private var categoryList:List<Category> = ArrayList()
    var onItemClick: ((Category) -> Unit)? =null


    fun setCategoryList(categoryList: List<Category>){
        this.categoryList = categoryList
        notifyDataSetChanged()
    }


   class CategoryViewHolder(val binding:CategoryItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.apply {
            tvCategoryName.text = categoryList[position].strCategory

            Glide.with(context)
                .load(categoryList[position].strCategoryThumb)
                .into(imgCategory)
        }

        holder.itemView.setOnClickListener {
            if(NetworkUtility.checkConnection(context))
            onItemClick?.invoke(categoryList[position])
        }


    }

    override fun getItemCount(): Int {
        return (categoryList.size)-2
    }

}