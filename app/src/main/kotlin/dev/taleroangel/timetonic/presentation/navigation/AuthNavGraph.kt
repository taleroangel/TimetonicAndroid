package dev.taleroangel.timetonic.presentation.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.taleroangel.timetonic.presentation.ui.views.auth.LoginView
import dev.taleroangel.timetonic.presentation.viewmodel.AuthViewModel

fun NavGraphBuilder.authNavGraph(
    navController: NavController,
    authViewModel: AuthViewModel,
) {
    composable(route = NavigationRoutes.AuthRoute.route) {
        LoginView { email, password -> false }
    }
}