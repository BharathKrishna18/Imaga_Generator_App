package com.bcorp.imagageneratorapp.config

enum class AppNavigation {
    LOGIN,
    SIGNUP,
    MAINSCREEN
}
sealed class Navigation(val route : String){
    object Login : Navigation(AppNavigation.LOGIN.name)
    object SignUp : Navigation(AppNavigation.SIGNUP.name)
    object MainScreen : Navigation(AppNavigation.MAINSCREEN.name)

}