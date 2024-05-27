package com.harsh_kumar.meals.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.harsh_kumar.meals.viewModels.RandomMealViewModel

@Composable
fun RandomMealScreen(navController: NavController) {
    val randomMealViewModel: RandomMealViewModel = viewModel()
    val randomMealState by randomMealViewModel.randomMeal

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            randomMealState.loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    //Text(text = "Loading ... ")
                }
            }

            randomMealState.error != null -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = randomMealState.error!!, color = Color.Red)
                }
            }

            else -> {
                randomMealState.meal?.let { RandomMealItem(it, navController) }
            }
        }
    }
}
