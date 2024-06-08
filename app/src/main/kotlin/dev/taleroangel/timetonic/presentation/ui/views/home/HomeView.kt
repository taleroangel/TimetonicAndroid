package dev.taleroangel.timetonic.presentation.ui.views.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.taleroangel.timetonic.R
import dev.taleroangel.timetonic.presentation.navigation.HomeRoutesNavBarItem
import dev.taleroangel.timetonic.presentation.navigation.NavigationRoutes
import dev.taleroangel.timetonic.presentation.ui.theme.TimetonicApplicationTheme

@Composable
fun HomeView(
    routes: List<HomeRoutesNavBarItem>,
    navController: NavController,
    content: @Composable () -> Unit,
) {
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    Scaffold(bottomBar = {
        NavigationBar {
            routes.forEachIndexed { idx, item ->
                NavigationBarItem(
                    selected = idx == selectedItem,
                    onClick = {
                        selectedItem = idx
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) { saveState = true }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = item.icon,
                    label = item.label,
                )
            }
        }
    }) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            content.invoke()
        }
    }
}