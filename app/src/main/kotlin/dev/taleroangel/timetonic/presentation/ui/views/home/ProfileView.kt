package dev.taleroangel.timetonic.presentation.ui.views.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.taleroangel.timetonic.R
import dev.taleroangel.timetonic.domain.entities.UserDetails
import dev.taleroangel.timetonic.presentation.ui.theme.TimetonicApplicationTheme

@Composable
fun ProfileView(
    profile: UserDetails?,
    onLogout: () -> Unit = {}
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .consumeWindowInsets(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
        ) {
            if (profile == null) {
                Text(
                    text = stringResource(id = R.string.login_failed),
                    style = MaterialTheme.typography.displayMedium,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = stringResource(id = R.string.login_failed_description),
                    textAlign = TextAlign.Center,
                )
            } else {

                Text(
                    stringResource(id = R.string.welcome_simple),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                )
                Text(
                    profile.name,
                    style = MaterialTheme.typography.displayMedium,
                    textAlign = TextAlign.Center,
                )
                Text(
                    profile.email,
                    textAlign = TextAlign.Center,
                )

                Button(
                    modifier = Modifier.padding(12.dp),
                    onClick = onLogout,
                ) {
                    Text(text = stringResource(id = R.string.logout))
                }
            }
        }
    }
}

@Composable
@Preview
fun ProfileViewPreview() {
    TimetonicApplicationTheme {
        ProfileView(
            UserDetails(
                books = 0,
                email = "john_doe@email.com",
                lastName = "Doe",
                firstName = "John"
            )
        )
    }
}

@Composable
@Preview
fun ProfileViewUnauthenticatedPreview() {
    TimetonicApplicationTheme {
        ProfileView(null)
    }
}