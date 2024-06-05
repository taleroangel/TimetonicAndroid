package dev.taleroangel.timetonic.presentation.ui.views.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.taleroangel.timetonic.presentation.ui.theme.TimetonicApplicationTheme

@Composable
fun HomeView() {
    Scaffold { paddingValues ->
        Box(modifier = Modifier.consumeWindowInsets(paddingValues)) {
            Text(text = "Hello World!")
        }
    }
}

@Composable
@Preview
fun HomeViewPreview() {
    TimetonicApplicationTheme {
        HomeView()
    }
}