package com.example.submissioncompose.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Bookmark : Screen("bookmark")
    object Profile : Screen("profile")
    object DetailScreen : Screen("home/{detailId}") {
        fun createRoute(detailId: String) = "home/$detailId"
    }
}