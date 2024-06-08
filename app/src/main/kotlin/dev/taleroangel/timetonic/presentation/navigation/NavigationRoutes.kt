package dev.taleroangel.timetonic.presentation.navigation

/**
 * Type safe navigation routes
 */
sealed class NavigationRoutes(val route: String) {
    data object AuthRoute : NavigationRoutes("/auth") {
        data object InitAuthRoute : NavigationRoutes(this.route + "/init")
        data object LoginAuthRoute : NavigationRoutes(this.route + "/login")
    }

    data object HomeRoute : NavigationRoutes("/home")
}