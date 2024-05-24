package com.harsh_kumar.meals.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.harsh_kumar.meals.views.AllScreen.*
import com.harsh_kumar.meals.ui.theme.GreenBg

@Composable
fun BottomNav(navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .background(color = GreenBg)
            .fillMaxWidth()
            .padding(20.dp),
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
                modifier = Modifier.size(100.dp)
            )
        }

        IconButton(onClick = {
            navController.navigate(RandomMealScreen.route)
        }) {
            Icon(
                Icons.Rounded.Home,
                contentDescription = "Home",
                tint = MaterialTheme.colorScheme.background,
                modifier = Modifier.size(100.dp)
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
                tint = MaterialTheme.colorScheme.background,
                modifier = Modifier.size(100.dp)
            )
        }

    }
}
