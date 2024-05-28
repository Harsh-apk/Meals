package com.harsh_kumar.meals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import com.harsh_kumar.meals.ui.theme.RentalsTheme
import com.harsh_kumar.meals.views.BottomNav
import com.harsh_kumar.meals.views.Navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            RentalsTheme {
                Scaffold(
                    bottomBar = {
                        BottomNav(navController = navController)
                    }
                ) { innerPadding ->
                    Navigation(navController, innerPadding)
                }
            }
        }

    }
}
