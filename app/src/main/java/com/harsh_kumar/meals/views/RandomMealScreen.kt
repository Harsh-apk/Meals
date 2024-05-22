package com.harsh_kumar.meals.views

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    val randomMealViewModel :RandomMealViewModel = viewModel();
    val viewState by randomMealViewModel.randomMeal

    Box(modifier = Modifier.fillMaxSize()){
        when{
            viewState.loading->{
                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center ) {
                    Text(text = "Loading ... ")
                }
            }
            viewState.error!=null ->{
                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center ) {
                    Text(text = viewState.error!!,color= Color.Red)
                }
            }
            else ->{
                viewState.meal?.let { RandomMealItem(it,navController) }
            }
        }
    }
}

@Composable
fun RandomMealItem(meal: Meal, navController: NavController) {
    val context = LocalContext.current
    var intent :Intent

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = GreenBg),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(

            painter = rememberAsyncImagePainter(meal.strMealThumb),
            contentDescription = "Thumbnail Image of Meal",
            modifier = Modifier
                .padding(top = 70.dp)
                .clip(
                    RoundedCornerShape(100)
                )
        )
        Text(
            meal.strMeal,
            fontSize = TextUnit(24F, TextUnitType.Sp),
            color = Color.White,
            modifier = Modifier.padding(top = 20.dp),
            fontWeight = FontWeight.Bold,
        )

        Box(
            modifier = Modifier
                .fillMaxHeight(0.85f)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp)
                )
                .background(color = Color.White)
                .padding(20.dp)


        )
        {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

                Text(
                    text = "Instructions",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(24F, TextUnitType.Sp),
                    modifier = Modifier.fillMaxWidth()
                )
                Text(text = meal.strInstructions, modifier = Modifier.padding(top = 20.dp))
                if (meal.strYoutube != null) {
                    IconButton(
                        onClick = {
                            intent = Intent(Intent.ACTION_VIEW, Uri.parse(meal.strYoutube))
                            context.startActivity(intent)
                        }, modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth()
                    ) {
                        Icon(
                            Icons.Sharp.PlayArrow,
                            contentDescription = "Play Button",
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }

                }
            }
        }
        BottomNav(navController = navController)
    }
}
