package dev.taleroangel.timetonic.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.taleroangel.timetonic.presentation.ui.views.home.HomeView

fun NavGraphBuilder.homeNavGraph() {
    composable(route = NavigationRoutes.HomeRoute.route) {
        HomeView()
    }
}

fun NavController.navigateToHome() {
    this.navigate(NavigationRoutes.HomeRoute.route) {
        popUpTo(0)
    }
}