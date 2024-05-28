package com.harsh_kumar.meals.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.harsh_kumar.meals.views.Screen.FavoriteScreen
import com.harsh_kumar.meals.views.Screen.RandomMealScreen
import com.harsh_kumar.meals.views.Screen.SearchMealScreen

@Composable
fun Navigation(navHostController: NavHostController, innerPadding: PaddingValues) {
    NavHost(navController = navHostController, startDestination = RandomMealScreen.route) {
        composable(route = RandomMealScreen.route) {
            RandomMealScreen(modifier = Modifier.padding(innerPadding))
        }
        composable(route = SearchMealScreen.route) {
            SearchMealScreen(modifier = Modifier.padding(innerPadding))
        }
        composable(route = FavoriteScreen.route) {
            FavoriteScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}
