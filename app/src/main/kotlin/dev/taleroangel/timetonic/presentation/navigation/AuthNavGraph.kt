package dev.taleroangel.timetonic.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.taleroangel.timetonic.presentation.ui.views.auth.LoginView

fun NavGraphBuilder.authNavGraph(
    navController: NavController
) {
    composable(route = NavigationRoutes.AuthRoute.route) {
        LoginView { email, password -> false }
    }
}