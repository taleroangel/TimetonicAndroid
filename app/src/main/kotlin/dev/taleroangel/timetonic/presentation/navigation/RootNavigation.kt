package dev.taleroangel.timetonic.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.taleroangel.timetonic.presentation.viewmodel.AuthViewModel

@Composable
fun RootNavigation(
    navigationController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = viewModel(),
) {
    NavHost(
        navController = navigationController,
        startDestination = NavigationRoutes.AuthRoute.route
    ) {
        authNavGraph(navigationController, authViewModel)
    }
}