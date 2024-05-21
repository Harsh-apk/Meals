package com.harsh_kumar.meals.types

data class Meal (
    val strMeal: String,
    val strCategory: String,
    val strInstructions:String,
    val strMealThumb:String,
    val strYoutube:String?,
)
data class MealResponse(val meals: List<Meal>)

