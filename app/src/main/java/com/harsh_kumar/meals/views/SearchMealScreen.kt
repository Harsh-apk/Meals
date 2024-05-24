package com.harsh_kumar.meals.views

import android.content.Context
import android.content.Intent
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
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.harsh_kumar.meals.model.Meal
import com.harsh_kumar.meals.ui.theme.GreenBg
import com.harsh_kumar.meals.viewModels.SearchMealViewModel

@Composable
fun SearchMealMainScreen(navController: NavController) {
    val searchViewModel: SearchMealViewModel = viewModel()
    val viewState by searchViewModel.searchMealState
    val searchQueue = searchViewModel.searchQuery
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .background(GreenBg)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Search Recipe",
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = 80.dp),
            color = Color.LightGray,
            fontFamily = FontFamily.Serif
        )

        Box(modifier = Modifier.padding(top = 30.dp)) {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                TextField(
                    value = searchQueue.value,
                    onValueChange = { text -> searchQueue.value = text },
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .fillMaxWidth(0.95F)
                        .clip(RoundedCornerShape(30.dp)),
                    singleLine = true,
                    placeholder = { Text(text = "Hungry? Search for your favorite dish!") },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                searchViewModel.fetchSearchMeal()
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            },
                            modifier = Modifier.padding(horizontal = 2.dp),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = GreenBg,
                                contentColor = MaterialTheme.colorScheme.onBackground
                            )
                        ) {
                            Icon(Icons.Rounded.Search, contentDescription = "Search")
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = MaterialTheme.colorScheme.inverseOnSurface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.inverseOnSurface
                    )
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(0.95F)
                .fillMaxHeight(0.89f)
                .padding(top = 12.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(color = MaterialTheme.colorScheme.inverseOnSurface)
        ) {

            when {
                viewState.loading -> {
                    BoxText(error = "Loading...")
                }

                viewState.error != null -> {
                    BoxText(viewState.error!!)
                }

                viewState.meals.isNullOrEmpty() -> {
                    BoxText(error = "If you haven't found the perfect recipe yet, try searching with different keywords.")
                }

                else -> {
                    viewState.meals?.let { MealList(meals = it) }
                }

            }
        }

        BottomNav(navController)

    }
}

@Composable
fun BoxText(error: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = error,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Serif,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
fun MealList(meals: List<Meal>) {
    val context = LocalContext.current
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(meals) { meal ->
            MealCard(meal = meal, context = context)
        }
    }
}

@Composable
fun MealCard(meal: Meal, context: Context) {
    var expanded by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            model = meal.thumbnail,
            contentDescription = "Image of meal",
            modifier = Modifier
                .padding(top = 24.dp)
                .clip(RoundedCornerShape(20.dp))
        )

        Row(verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = meal.name,
                modifier = Modifier
                    .padding(top = 10.dp),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = FontFamily.Serif
            )

            IconButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown,
                    contentDescription = "See More",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

        }

        if (expanded) {
            Text(
                text = meal.instructions,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = FontFamily.Serif
            )

            Button(
                onClick = {
                    val contentToShare = "${meal.name}\n\n${meal.instructions}"
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, contentToShare)
                    }
                    context.startActivity(Intent.createChooser(shareIntent, null))
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = GreenBg,
                    contentColor = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share recipe"
                    )
                    Text(
                        text = "Share",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

    }
}
