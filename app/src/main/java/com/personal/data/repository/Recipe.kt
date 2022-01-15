package com.personal.data.repository
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



data class Recipe(
    var recipeId: Int,
    var recipeName: String,
    var cuisineType: String,
    var calories: Double,
    var image : String,
    var ingredients: String,
    var expanded: Boolean = false
)