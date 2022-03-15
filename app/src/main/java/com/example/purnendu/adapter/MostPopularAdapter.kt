package com.example.purnendu.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.purnendu.NetworkUtility
import com.example.purnendu.databinding.PopularItemBinding
import com.example.purnendu.pojo.MealsByCategoryList


class MostPopularAdapter(private  val context: Context) : RecyclerView.Adapter<MostPopularAdapter.MyViewHolder>() {

    private  var  mealList :List<MealsByCategoryList.Meal> = ArrayList()

    fun setAdapter(mealListMealsBy :List<MealsByCategoryList.Meal>)
    {
        this.mealList=mealListMealsBy
        notifyDataSetChanged()
    }

    lateinit var onItemClick:((MealsByCategoryList.Meal)->Unit)


    class MyViewHolder( val binding: PopularItemBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       Glide.with(context).load(mealList[position].strMealThumb)
           .into(holder.binding.imgPopularMeal)

        holder.itemView.setOnClickListener {
            if(NetworkUtility.checkConnection(context))
            onItemClick.invoke(mealList[position])
        }
    }

    override fun getItemCount(): Int {
        return  mealList.size
    }
}