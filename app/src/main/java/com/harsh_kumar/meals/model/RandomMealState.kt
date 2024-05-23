package com.harsh_kumar.meals.model

data class RandomMealState(
    val loading: Boolean = true,
    val meal: Meal? = null,
    val error: String? = null
)
