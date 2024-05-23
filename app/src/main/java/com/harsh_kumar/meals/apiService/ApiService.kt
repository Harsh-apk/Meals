package com.harsh_kumar.meals.apiService

import com.harsh_kumar.meals.model.MealResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit = Retrofit.Builder().baseUrl("https://www.themealdb.com/api/json/v1/1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
val mealService: ApiService = retrofit.create(ApiService::class.java)

interface ApiService {

    @GET("random.php")
    suspend fun getRandomMeal(): MealResponse

    @GET("search.php")
    suspend fun searchMeal(@Query("s") s: String): MealResponse

}
