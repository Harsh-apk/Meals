package com.harsh_kumar.meals.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.sharp.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.harsh_kumar.meals.model.IngredientWithMeasurement
import com.harsh_kumar.meals.model.Meal
import com.harsh_kumar.meals.ui.theme.GreenBg

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
    LazyColumn(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(vertical = 4.dp)) {
        items(meals) { meal ->
            MealCard(meal = meal, context = context)
            HorizontalDivider(thickness = 2.dp)
        }
    }
}

@Composable
fun MealCard(meal: Meal, context: Context) {
    var expanded by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            model = meal.thumbnail,
            contentDescription = "Image of meal",
            modifier = Modifier
                .padding(top = 12.dp)
                .clip(RoundedCornerShape(30.dp))
        )

        Row(verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = meal.name,
                modifier = Modifier
                    .padding(top = 10.dp),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.ExtraBold
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

            HorizontalDivider(thickness = 1.5.dp)

            Text(
                text = "Ingredients",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = FontFamily.Serif
            )

            Ingredients(ingredients = meal.getIngredients(), modifier = Modifier.padding(8.dp))

            HorizontalDivider(thickness = 1.5.dp)

            Text(
                text = "Instructions",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = FontFamily.Serif
            )

            Text(
                text = meal.instructions,
                modifier = Modifier.padding(horizontal = 8.dp),
                color = MaterialTheme.colorScheme.onBackground,
            )

            HorizontalDivider(thickness = 1.5.dp, modifier = Modifier.padding(top = 8.dp))

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
                modifier = Modifier.padding(top = 8.dp)
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

@Composable
fun MealImage(thumbnail: String?) {
    Image(
        painter = rememberAsyncImagePainter(thumbnail),
        contentDescription = "Thumbnail Image of Meal",
        modifier = Modifier
            .padding(top = 36.dp)
            .clip(RoundedCornerShape(10)),
    )
}

@Composable
fun Ingredients(ingredients: List<IngredientWithMeasurement>, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        ingredients.forEach { ingredient ->
            Row {
                Text(
                    text = ingredient.ingredient + ":",
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = ingredient.measurement,
                    color = MaterialTheme.colorScheme.onBackground
                )
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
            style = MaterialTheme.typography.headlineMedium,
            color = Color.LightGray,
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )

        Text(
            text = "origin: " + meal.origin,
            color = Color.LightGray,
            modifier = Modifier.padding(bottom = 12.dp),
            fontWeight = FontWeight.Thin,
            fontFamily = FontFamily.Serif,
            fontStyle = FontStyle.Italic
        )

        Box(
            modifier = Modifier.clip(RoundedCornerShape(30.dp))
            //.background(color = Color.LightGray)
            //.padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    //.fillMaxSize()
                    //.fillMaxHeight(0.85f)
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
                        text = "Ingredients",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontFamily = FontFamily.Serif
                    )

                    Ingredients(
                        ingredients = meal.getIngredients(),
                        modifier = Modifier.padding(8.dp)
                    )

                    HorizontalDivider(thickness = 1.5.dp)

                    Text(
                        text = "Instructions",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontFamily = FontFamily.Serif
                    )

                    Text(
                        text = meal.instructions,
                        modifier = Modifier.padding(top = 4.dp, start = 8.dp, end = 8.dp),
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        if (meal.youtubeLink != null) {
                            Button(
                                onClick = {
                                    intent = Intent(Intent.ACTION_VIEW, Uri.parse(meal.youtubeLink))
                                    context.startActivity(intent)
                                },
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
                                    Text(
                                        text = "Preview",
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }

                            }
                        }

                        Button(
                            onClick = {
                                val contentToShare = "${meal.name}\n${meal.instructions}"
                                intent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, contentToShare)
                                }
                                context.startActivity(Intent.createChooser(intent, null))
                            },
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

                } // Column
            }
        } // Box

        BottomNav(navController = navController)

    }
}
