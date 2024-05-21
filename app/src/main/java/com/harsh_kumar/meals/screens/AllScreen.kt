package com.harsh_kumar.meals.screens

sealed class AllScreen(val route:String) {
    object RandomMealScreen: AllScreen("Random")
    object SearchMealScreen : AllScreen("Search")
    object FavoriteScreen: AllScreen("Favorite")
}