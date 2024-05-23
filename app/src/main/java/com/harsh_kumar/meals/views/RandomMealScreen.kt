package com.harsh_kumar.meals.views

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.harsh_kumar.meals.model.Meal
import com.harsh_kumar.meals.ui.theme.GreenBg
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

@Composable
fun RandomMealItem(meal: Meal, navController: NavController) {
    val context = LocalContext.current
    var intent: Intent

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = GreenBg),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        MealImage(meal.thumbnail)

        Text(
            text = meal.name,
            fontSize = TextUnit(24F, TextUnitType.Sp),
            color = Color.LightGray,
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp),
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
            //.background(color = Color.LightGray)
            //.padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.85f)
                    .fillMaxWidth(0.95f)
                    .clip(RoundedCornerShape(30.dp))
                    .background(color = MaterialTheme.colorScheme.background)
            ) {

                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Recipe",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = TextUnit(24F, TextUnitType.Sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontFamily = FontFamily.Serif
                    )

                    Text(
                        text = meal.instructions,
                        modifier = Modifier.padding(top = 12.dp, start = 8.dp, end = 8.dp),
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    if (meal.youtubeLink != null) {
                        Button(
                            onClick = {
                                intent = Intent(Intent.ACTION_VIEW, Uri.parse(meal.youtubeLink))
                                context.startActivity(intent)
                            },
                            modifier = Modifier.padding(top = 20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GreenBg,
                                contentColor = MaterialTheme.colorScheme.onBackground
                            )
                        ) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    Icons.Sharp.PlayArrow,
                                    contentDescription = "Play Button",
                                )
                                Text(text = "Preview", modifier = Modifier.padding(start = 8.dp))
                            }

                        }
                    }

                } // Column
            }
        } // Box

        BottomNav(navController = navController)

    }
}

@Composable
fun MealImage(thumbnail: String?) {
    Image(
        painter = rememberAsyncImagePainter(thumbnail),
        contentDescription = "Thumbnail Image of Meal",
        modifier = Modifier
            .padding(top = 50.dp)
            .clip(RoundedCornerShape(10)),
    )
}
