package dev.taleroangel.timetonic.presentation.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.taleroangel.timetonic.infrastructure.exception.ExpiredTokenException
import dev.taleroangel.timetonic.presentation.navigation.RootNavigation
import dev.taleroangel.timetonic.presentation.navigation.navigateToAuthentication
import dev.taleroangel.timetonic.presentation.navigation.navigateToHome
import dev.taleroangel.timetonic.presentation.ui.state.AuthViewState
import dev.taleroangel.timetonic.presentation.ui.theme.TimetonicApplicationTheme
import dev.taleroangel.timetonic.presentation.viewmodel.AuthViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TimetonicApplicationTheme {
                RootNavigation()
            }
        }
    }
}