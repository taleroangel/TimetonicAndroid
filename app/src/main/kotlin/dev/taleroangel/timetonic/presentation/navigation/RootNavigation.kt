package dev.taleroangel.timetonic.presentation.navigation

import android.widget.Toast
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.taleroangel.timetonic.R
import dev.taleroangel.timetonic.presentation.ui.state.AuthViewState
import dev.taleroangel.timetonic.presentation.viewmodel.AuthViewModel

@Composable
fun RootNavigation(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = viewModel(),
) {
    // Get authentication status
    val authenticationStatus by authViewModel.authState.observeAsState()
    val authUser by authViewModel.userDetails.observeAsState()

    when (authenticationStatus) {
        AuthViewState.NONE -> navController.navigateToAuthentication()
        AuthViewState.AUTHENTICATED -> {
            Toast.makeText(
                LocalContext.current,
                stringResource(id = R.string.welcome, authUser!!.name),
                Toast.LENGTH_SHORT
            ).show()
            navController.navigateToHome()
        }
        else -> {}
    }

    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.AuthRoute.route,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start) }
    ) {
        authNavGraph(navController, authViewModel)
        homeNavGraph(navController, authViewModel)
    }
}