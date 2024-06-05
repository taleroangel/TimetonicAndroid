package dev.taleroangel.timetonic.presentation.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import dev.taleroangel.timetonic.infrastructure.exception.ExpiredTokenException
import dev.taleroangel.timetonic.presentation.navigation.RootNavigation
import dev.taleroangel.timetonic.presentation.ui.theme.TimetonicApplicationTheme

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