package com.harsh_kumar.meals.views

sealed class AllScreen(val route: String) {
    object RandomMealScreen : AllScreen("Random")
    object SearchMealScreen : AllScreen("Search")
    object FavoriteScreen : AllScreen("Favorite")
}
