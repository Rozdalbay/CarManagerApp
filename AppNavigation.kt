package com.example.carcosts.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.carcosts.ui.screens.CarListScreen
import com.example.carcosts.ui.screens.ExpenseListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "cars") {
        composable("cars") {
            CarListScreen(onCarClick = { carId ->
                navController.navigate("expenses/$carId")
            })
        }
        composable(
            route = "expenses/{carId}",
            arguments = listOf(navArgument("carId") { type = NavType.IntType })
        ) {
            ExpenseListScreen(navController = navController)
        }
    }
}