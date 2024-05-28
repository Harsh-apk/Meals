package com.harsh_kumar.meals.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Shuffle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.harsh_kumar.meals.ui.theme.GreenBg
import com.harsh_kumar.meals.views.Screen.FavoriteScreen
import com.harsh_kumar.meals.views.Screen.RandomMealScreen
import com.harsh_kumar.meals.views.Screen.SearchMealScreen

@Composable
fun BottomNav(navController: NavHostController) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .background(color = GreenBg)
            .fillMaxWidth()
            .padding(bottom = 16.dp),
    ) {
        IconButton(onClick = {
            navController.navigate(SearchMealScreen.route) {
                popUpTo(RandomMealScreen.route) { inclusive = false }
            }
        }) {
            Icon(
                Icons.Rounded.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.background,
            )
        }

        IconButton(onClick = {
            navController.navigate(RandomMealScreen.route)
        }) {
            Icon(
                Icons.Rounded.Shuffle,
                contentDescription = "Home",
                tint = MaterialTheme.colorScheme.background,
            )
        }

        IconButton(onClick = {
            navController.navigate(FavoriteScreen.route) {
                popUpTo(RandomMealScreen.route) { inclusive = false }
            }
        }) {
            Icon(
                Icons.Rounded.Favorite,
                contentDescription = "Favorite",
                tint = MaterialTheme.colorScheme.background
            )
        }

    }
}
