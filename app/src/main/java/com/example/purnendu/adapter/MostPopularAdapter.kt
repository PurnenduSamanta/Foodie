package com.example.purnendu.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.purnendu.databinding.PopularItemBinding
import com.example.purnendu.pojo.MealsByCategoryList


class MostPopularAdapter : RecyclerView.Adapter<MostPopularAdapter.MyViewHolder>() {

    private lateinit var  mealListMealsBy :List<MealsByCategoryList.Meal>

    fun setAdapter(mealListMealsBy :List<MealsByCategoryList.Meal>)
    {
        this.mealListMealsBy=mealListMealsBy
    }

    lateinit var onItemClick:((MealsByCategoryList.Meal)->Unit)


    class MyViewHolder( val binding: PopularItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       Glide.with(holder.itemView).load(mealListMealsBy[position].strMealThumb)
           .into(holder.binding.imgPopularMeal)


        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealListMealsBy[position])
        }
    }

    override fun getItemCount(): Int {
        return  mealListMealsBy.size
    }
}