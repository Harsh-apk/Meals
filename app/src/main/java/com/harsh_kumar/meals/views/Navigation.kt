package com.harsh_kumar.meals.views

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.harsh_kumar.meals.views.AllScreen.*

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = RandomMealScreen.route) {
        composable(route = RandomMealScreen.route) {
            RandomMealScreen(navController = navController)
        }
        composable(route = SearchMealScreen.route) {
            SearchMealMainScreen(navController = navController)
        }
        composable(route = FavoriteScreen.route) {
            FavoriteScreen(navController = navController)
        }
    }
}
