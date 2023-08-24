package com.example.wenotehub.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wenotehub.ui.screen.CreateScreen
import com.example.wenotehub.ui.screen.FinishedScreen
import com.example.wenotehub.ui.screen.HomeScreen
import com.example.wenotehub.ui.screen.SettingsScreen

@Composable
fun NavGraph(navController: NavHostController, onChangeState: () -> Unit) {
    NavHost(navController = navController, startDestination = ScreenNavGraph.HomeScreen.route){
        composable(route = ScreenNavGraph.HomeScreen.route){
            HomeScreen(navController)
        }
        composable(route = ScreenNavGraph.FinishedScreen.route){
            FinishedScreen(navController)
        }
        composable(route = ScreenNavGraph.SettingsScreen.route){
            SettingsScreen(navController)
        }
        composable(route = ScreenNavGraph.CreateScreen.route){
            LaunchedEffect(Unit){
                onChangeState()
            }
            CreateScreen(navController)
        }
    }
}