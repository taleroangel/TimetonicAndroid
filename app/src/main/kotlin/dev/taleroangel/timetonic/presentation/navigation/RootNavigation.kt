package dev.taleroangel.timetonic.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun RootNavigation(
    navigationController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navigationController, startDestination = "") {
        authNavGraph()
    }
}