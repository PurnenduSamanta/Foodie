package com.example.purnendu.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorite Meal")
data class MealModel(
    @PrimaryKey val mealId: String,
    val mealName: String,
    val mealThumb: String
)