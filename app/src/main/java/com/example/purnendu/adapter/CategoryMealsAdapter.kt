package com.example.purnendu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.purnendu.databinding.CategoryItemBinding
import com.example.purnendu.pojo.MealsByCategoryList
import com.example.purnendu.room.MealModel

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.MealViewHolder>() {

    private var mealList: List<MealsByCategoryList.Meal> = ArrayList()
    lateinit var setOnMealClickListener: ((MealsByCategoryList.Meal)->Unit)

    fun setCategoryList(mealList: List<MealsByCategoryList.Meal>) {
        this.mealList = mealList
        notifyDataSetChanged()
    }

//    fun setOnMealClickListener(setOnMealClickListener: SetOnMealClickListener) {
//        this.setOnMealClickListener = setOnMealClickListener
//    }

    class MealViewHolder(val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.binding.apply {
            imgCategory.scaleType= ImageView.ScaleType.CENTER_CROP
            tvCategoryName.text = mealList[position].strMeal
            Glide.with(holder.itemView)
                .load(mealList[position].strMealThumb)
                .into(imgCategory)
        }

        holder.itemView.setOnClickListener {
          setOnMealClickListener.invoke(mealList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }
}

//interface SetOnMealClickListener {
//    fun setOnClickListener(meal: MealsByCategoryList.Meal)
//}
