package dev.taleroangel.timetonic.presentation.navigation

import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.taleroangel.timetonic.R
import dev.taleroangel.timetonic.presentation.ui.state.AuthViewState
import dev.taleroangel.timetonic.presentation.ui.views.auth.LoginView
import dev.taleroangel.timetonic.presentation.ui.views.auth.TryLoginView
import dev.taleroangel.timetonic.presentation.viewmodel.AuthViewModel

fun NavGraphBuilder.authNavGraph(
    navController: NavController,
    authViewModel: AuthViewModel,
) {
    navigation(
        route = NavigationRoutes.AuthRoute.route,
        startDestination = NavigationRoutes.AuthRoute.InitAuthRoute.route
    ) {

        composable(route = NavigationRoutes.AuthRoute.InitAuthRoute.route) {
            // Check if login is required
            val state by authViewModel.authState.observeAsState()
            when (state) {
                AuthViewState.NONE -> navController.navigateToAuthenticationLogin()
                AuthViewState.AUTHENTICATED -> navController.navigateToHome()
                else -> {}
            }

            TryLoginView()
        }

        composable(route = NavigationRoutes.AuthRoute.LoginAuthRoute.route) {

            // Observe the state and the authenticated user
            val state by authViewModel.authState.observeAsState()
            val user by authViewModel.userDetails.observeAsState()
            val rememberCredentials by authViewModel.rememberCredentials.collectAsState()

            when (state) {
                AuthViewState.AUTHENTICATED -> {
                    Toast.makeText(
                        LocalContext.current,
                        stringResource(id = R.string.welcome, user!!.name),
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigateToHome()
                }

                AuthViewState.INIT -> {
                    navController.navigateToAuthenticationInit()
                }

                else -> {}
            }

            LoginView(
                showWaitDialog = state == AuthViewState.AUTHENTICATING,
                showErrorDialog = state == AuthViewState.FAILED,
                onDismissErrorDialog = { authViewModel.restart() },
                rememberCredentials = rememberCredentials,
                onRememberCredentialsChange = { value ->
                    authViewModel.rememberCredentials.value = value
                }
            ) { email, password ->
                // Authenticate user in remote service
                authViewModel.authenticate(email, password)
            }
        }
    }
}

internal fun NavController.navigateToAuthenticationInit() {
    this.navigate(NavigationRoutes.AuthRoute.InitAuthRoute.route) {
        popUpTo(0)
    }
}

internal fun NavController.navigateToAuthenticationLogin() {
    this.navigate(NavigationRoutes.AuthRoute.LoginAuthRoute.route) {
        popUpTo(0)
    }
}

fun NavController.navigateToAuthentication() {
    this.navigate(NavigationRoutes.AuthRoute.route) {
        popUpTo(0)
    }
}