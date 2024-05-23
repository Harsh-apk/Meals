package com.harsh_kumar.meals.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.navigation.NavController
import com.harsh_kumar.meals.ui.theme.GreenBg

@Composable
fun FavoriteScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .background(color = GreenBg)
                .fillMaxWidth()
                .fillMaxHeight(0.92f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Coming Soon ...",
                fontSize = TextUnit(value = 24f, TextUnitType.Sp),
                color = MaterialTheme.colorScheme.background
            )

        }
        BottomNav(navController)
    }
}
