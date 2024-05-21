package com.harsh_kumar.meals.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AllScreen.RandomMealScreen.route){
        composable(route = AllScreen.RandomMealScreen.route){
            RandomMealScreen(navController=navController)
        }
        composable(route = AllScreen.SearchMealScreen.route){
            SearchMealMainScreen(navController=navController)
        }
        composable(route = AllScreen.FavoriteScreen.route){
            FavoriteScreen(navController=navController)
        }
    }
}
