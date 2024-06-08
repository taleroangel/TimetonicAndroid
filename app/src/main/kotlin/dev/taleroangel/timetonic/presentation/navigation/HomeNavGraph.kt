package dev.taleroangel.timetonic.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.taleroangel.timetonic.R
import dev.taleroangel.timetonic.presentation.ui.views.home.BooksView
import dev.taleroangel.timetonic.presentation.ui.views.home.HomeView
import dev.taleroangel.timetonic.presentation.ui.views.home.ProfileView
import dev.taleroangel.timetonic.presentation.viewmodel.AuthViewModel

data class HomeRoutesNavBarItem(
    val route: String,
    val icon: @Composable () -> Unit = {},
    val label: @Composable () -> Unit = {},
    val content: @Composable () -> Unit,
)

fun NavGraphBuilder.homeNavGraph(
    navController: NavController,
    authViewModel: AuthViewModel
) {

    /**
     * List of routes available to home
     */
    val routes: List<HomeRoutesNavBarItem> = listOf(
        HomeRoutesNavBarItem(
            route = NavigationRoutes.HomeRoute.BooksHomeRoute.route,
            icon = {
                Icon(
                    Icons.Rounded.Book,
                    contentDescription = stringResource(id = R.string.books_title)
                )
            },
            label = {
                Text(text = stringResource(id = R.string.books_title))
            },
            content = { BooksView() },
        ),
        HomeRoutesNavBarItem(
            route = NavigationRoutes.HomeRoute.ProfileHomeRoute.route,
            icon = {
                Icon(
                    Icons.Rounded.Person,
                    contentDescription = stringResource(id = R.string.profile_title)
                )
            }, label = {
                Text(text = stringResource(id = R.string.profile_title))
            },
            content = {
                // Observe the profile
                val profile by authViewModel.userDetails.observeAsState()
                ProfileView(profile) {
                    // Return to authentication
                    navController.navigateToAuthentication()
                    // Close session
                    authViewModel.logout()
                }
            }
        ),
    )

    composable(NavigationRoutes.HomeRoute.route) {
        // Navigation controller
        val homeNavController = rememberNavController()

        HomeView(
            routes = routes,
            navController = homeNavController,
        ) {
            NavHost(
                navController = homeNavController,
                startDestination = NavigationRoutes.HomeRoute.BooksHomeRoute.route,
                enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
                exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
            ) {
                routes.forEach { item ->
                    composable(item.route) {
                        item.content.invoke()
                    }
                }
            }
        }
    }
}

fun NavController.navigateToHome() {
    this.navigate(NavigationRoutes.HomeRoute.route) {
        popUpTo(0)
    }
}