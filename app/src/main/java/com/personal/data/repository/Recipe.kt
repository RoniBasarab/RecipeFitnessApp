package com.personal.data.repository

data class Recipe(
    var calories: Double,
    var image : String,
    var recipeName: String,
    var cuisineType: String,
    var ingredients: List<String>,
    var isChosen: Boolean = false
)