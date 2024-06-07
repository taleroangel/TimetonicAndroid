package dev.taleroangel.timetonic.presentation.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.taleroangel.timetonic.presentation.ui.state.AuthViewState
import dev.taleroangel.timetonic.presentation.ui.views.auth.LoginView
import dev.taleroangel.timetonic.presentation.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.getAndUpdate

fun NavGraphBuilder.authNavGraph(
    navController: NavController,
    authViewModel: AuthViewModel,
) {
    composable(route = NavigationRoutes.AuthRoute.route) {

        val state by authViewModel.authState.collectAsState()

        if (state == AuthViewState.AUTHENTICATED) {
            navController.navigateToHome()
        }

        LoginView(
            showWaitDialog = state == AuthViewState.AUTHENTICATING,
            showErrorDialog = state == AuthViewState.FAILED,
            onDismissErrorDialog = { authViewModel.restart() },
        ) { email, password ->
            // Authenticate user in remote service
            authViewModel.authenticate(email, password)
        }
    }
}

fun NavController.navigateToAuthentication() {
    this.navigate(NavigationRoutes.AuthRoute.route) {
        popUpTo(0)
    }
}