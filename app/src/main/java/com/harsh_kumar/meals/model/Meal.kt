package com.harsh_kumar.meals.model

import com.google.gson.annotations.SerializedName

data class Meal(
    @SerializedName("strMeal")
    val name: String,

    @SerializedName("strCategory")
    val category: String,

    @SerializedName("strInstructions")
    val instructions: String,

    @SerializedName("strMealThumb")
    val thumbnail: String,

    @SerializedName("strYoutube")
    val youtubeLink: String?,
)
