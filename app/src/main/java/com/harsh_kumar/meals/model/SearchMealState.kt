package com.harsh_kumar.meals.model

data class SearchMealState(
    val loading: Boolean = false,
    val meals: List<Meal>? = null,
    val error: String? = null
)
