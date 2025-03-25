package com.bcorp.imagageneratorapp.config

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bcorp.imagageneratorapp.screens.LoginScreen
import com.bcorp.imagageneratorapp.screens.MainScreen
import com.bcorp.imagageneratorapp.screens.SignUpScreen

@Composable
fun NavScreen(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Navigation.Login.route) {
        composable(Navigation.Login.route) {
            LoginScreen(navController)
        }
        composable(Navigation.SignUp.route) {
            SignUpScreen(navController)
        }
        composable(Navigation.MainScreen.route) {
            MainScreen(navController)
        }
    }
}