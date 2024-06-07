package dev.taleroangel.timetonic.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.taleroangel.timetonic.presentation.viewmodel.AuthViewModel

@Composable
fun RootNavigation(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = viewModel(),
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.AuthRoute.route,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start) }
    ) {
        authNavGraph(navController, authViewModel)
        homeNavGraph()
    }
}