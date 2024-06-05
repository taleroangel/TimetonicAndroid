package dev.taleroangel.timetonic.presentation.ui.views.auth

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.taleroangel.timetonic.R
import dev.taleroangel.timetonic.presentation.ui.theme.TimetonicApplicationTheme

/**
 * Request login credentials and authenticate
 */
@Composable
fun LoginView(
    onContinue: (email: String, password: String) -> Boolean,
) {
    var email: String by remember { mutableStateOf("") }
    var emailError: Boolean by remember { mutableStateOf(false) }
    var password: String by remember { mutableStateOf("") }
    var passwordError: Boolean by remember { mutableStateOf(false) }
    var showPassword: Boolean by remember { mutableStateOf(false) }
    var showAlertDialog: Boolean by remember { mutableStateOf(false) }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .consumeWindowInsets(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                14.dp, alignment = Alignment.CenterVertically
            ),
        ) {

            Box(modifier = Modifier.size(90.dp)) {
                Image(
                    painterResource(id = R.drawable.timetonic),
                    contentDescription = stringResource(id = R.string.app_name),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            Text(
                text = stringResource(id = R.string.display_name),
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            OutlinedTextField(
                value = email,
                isError = emailError,
                supportingText = {
                    AnimatedVisibility(visible = emailError) {
                        Text(text = stringResource(id = R.string.field_empty_error))
                    }
                },
                singleLine = true,
                label = { Text(text = stringResource(id = R.string.email)) },
                onValueChange = { email = it; emailError = false; },
            )

            OutlinedTextField(
                value = password,
                isError = passwordError,
                supportingText = {
                    AnimatedVisibility(visible = passwordError) {
                        Text(text = stringResource(id = R.string.field_empty_error))
                    }
                },
                singleLine = true,
                label = { Text(text = stringResource(id = R.string.password)) },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                onValueChange = { password = it; passwordError = false; },
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            imageVector = if (showPassword) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                            contentDescription = stringResource(id = R.string.password_visibility)
                        )
                    }
                },
            )

            Button(
                onClick = {
                    // Check parameters
                    var validationSuccess = true;

                    if (email.isEmpty()) {
                        emailError = true;
                        validationSuccess = false;
                    }
                    if (password.isEmpty()) {
                        passwordError = true;
                        validationSuccess = false;
                    }

                    // Call login
                    if (validationSuccess && !onContinue.invoke(email, password)) {
                        showAlertDialog = true
                    }
                },
            ) {
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = stringResource(id = R.string.continue_label),
                )
            }
        }
    }

    if (showAlertDialog) {
        AlertDialog(onDismissRequest = { showAlertDialog = false },
            title = { Text(text = stringResource(id = R.string.login_failed)) },
            text = { Text(text = stringResource(id = R.string.login_failed_description)) },
            confirmButton = {
                Button(onClick = { showAlertDialog = false }) {
                    Text(text = stringResource(id = R.string.ok_label))
                }
            })
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
fun LoginViewLightPreview() {
    TimetonicApplicationTheme {
        LoginView { _, _ -> false }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun LoginViewDarkPreview() {
    TimetonicApplicationTheme {
        LoginView { _, _ -> false }
    }
}