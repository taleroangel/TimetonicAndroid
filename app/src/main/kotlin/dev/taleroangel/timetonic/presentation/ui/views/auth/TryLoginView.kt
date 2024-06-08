package dev.taleroangel.timetonic.presentation.ui.views.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.taleroangel.timetonic.R
import dev.taleroangel.timetonic.presentation.ui.theme.TimetonicApplicationTheme

@Composable
fun TryLoginView() {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            Text(
                text = stringResource(id = R.string.login_label),
                style = MaterialTheme.typography.headlineMedium
            )
            Text(text = stringResource(id = R.string.wait))
            CircularProgressIndicator(modifier = Modifier.padding(12.dp))
        }
    }
}

@Composable
@Preview
fun TryLoginViewPreview() {
    TimetonicApplicationTheme {
        TryLoginView()
    }
}