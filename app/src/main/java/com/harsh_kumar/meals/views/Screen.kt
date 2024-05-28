package com.harsh_kumar.meals.views

sealed class Screen(val route: String) {
    object RandomMealScreen : Screen("Random")
    object SearchMealScreen : Screen("Search")
    object FavoriteScreen : Screen("Favorite")
}
