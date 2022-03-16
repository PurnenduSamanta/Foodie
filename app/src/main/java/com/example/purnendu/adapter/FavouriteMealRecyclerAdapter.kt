package com.example.purnendu.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.purnendu.NetworkUtility
import com.example.purnendu.databinding.CategoryItemBinding
import com.example.purnendu.room.MealModel

class FavouriteMealRecyclerAdapter(private val context: Context) :
    RecyclerView.Adapter<FavouriteMealRecyclerAdapter.MyViewHolder>() {

    private var mealList: List<MealModel> = ArrayList()
    lateinit var onItemSelected: ((MealModel) -> Unit)


    fun setFavouriteList(mealList: List<MealModel>) {
        this.mealList = mealList
        notifyDataSetChanged()
    }


    class MyViewHolder(val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            imgCategory.scaleType = ImageView.ScaleType.CENTER_CROP
            tvCategoryName.text = mealList[position].mealName
            Glide.with(holder.itemView)
                .load(mealList[position].mealThumb)
                .into(imgCategory)

            holder.itemView.setOnClickListener {
                if(NetworkUtility.checkConnection(context))
                onItemSelected.invoke(mealList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }
}