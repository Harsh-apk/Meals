package com.harsh_kumar.meals.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
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
        /*Text(
            text = "Search Recipe",
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = 40.dp),
            color = Color.LightGray,
            fontFamily = FontFamily.Serif
        )*/

        Box(modifier = Modifier.padding(top = 40.dp)) {

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
