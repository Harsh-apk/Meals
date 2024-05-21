package com.harsh_kumar.meals.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.harsh_kumar.meals.types.Meal
import com.harsh_kumar.meals.ui.theme.GreenBg
import com.harsh_kumar.meals.viewModels.SearchMealViewModel

@Composable
fun SearchMealMainScreen(navController: NavController){
    val searchViewModel : SearchMealViewModel = viewModel()
    val viewState by searchViewModel.searchMealState
    val searchQueue = searchViewModel.searchQuery

    Column(modifier = Modifier
        .background(GreenBg)
        .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Search", fontSize = TextUnit(40F, TextUnitType.Sp), modifier = Modifier.padding(top=80.dp))
        Box(modifier = Modifier.padding(top =30.dp)){
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                TextField(value = searchQueue.value, onValueChange = {text -> searchQueue.value=text},modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .clip(
                        RoundedCornerShape(30.dp)
                    ))
                IconButton(onClick = { searchViewModel.fetchSearchMeal() }, modifier = Modifier.padding(horizontal = 2.dp)) {
                    Icon(Icons.Rounded.Search, contentDescription ="Search" )

                }


            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(top = 30.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(color = Color.White)
            .padding(30.dp)
        ){

            when {
                viewState.loading ->{
                    Text(text = "Loading...")
                }
                viewState.error!=null -> {
                    Text(text = viewState.error!!)
                }else -> {
                viewState.meal?.let { SearchMeal(it) }

                }
            }
        }
        BottomNav(navController)
    }
}

@Composable
fun SearchMeal(meals : List<Meal>){
    LazyColumn(modifier = Modifier.fillMaxWidth() ) {
        items(meals ){
            meal-> ResponsiveImageCard(meal = meal)
        }
    }



//    Image(
//
//        painter = rememberAsyncImagePainter(meals[0].strMealThumb),
//        contentDescription = "Thumbnail Image of Meal",
//        modifier = Modifier
//            .padding(top = 70.dp)
//            .clip(
//                RoundedCornerShape(100)
//            )
//    )
}
@Composable
fun ResponsiveImageCard(meal: Meal){
    val check  = remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp), horizontalAlignment = Alignment.CenterHorizontally){
        AsyncImage(model=meal.strMealThumb,contentDescription ="Image of meal", modifier = Modifier.clip(
            RoundedCornerShape(20.dp)
        ) )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = meal.strMeal, modifier = Modifier.padding(top=10.dp))
            IconButton(onClick = { check.value= true }, modifier = Modifier.padding(top=10.dp)) {
                Icon(Icons.Rounded.ArrowDropDown, contentDescription = "See More")
            }
        }
        if(check.value){
            Text(text = meal.strInstructions, modifier = Modifier.padding(start = 10.dp, end = 10.dp))
        }

    }
}
