package dev.taleroangel.timetonic.presentation.ui.views.auth

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dev.taleroangel.timetonic.R
import dev.taleroangel.timetonic.presentation.ui.theme.TimetonicApplicationTheme

/**
 * Request login credentials and authenticate
 */
@Composable
fun LoginView(
    showWaitDialog: Boolean = false,
    showErrorDialog: Boolean = false,
    onDismissErrorDialog: () -> Unit = {},
    rememberCredentials: Boolean,
    onRememberCredentialsChange: (Boolean) -> Unit,
    onContinue: (email: String, password: String) -> Unit,
) {
    var email: String by remember { mutableStateOf("") }
    var emailError: Boolean by remember { mutableStateOf(false) }
    var password: String by remember { mutableStateOf("") }
    var passwordError: Boolean by remember { mutableStateOf(false) }
    var showPassword: Boolean by remember { mutableStateOf(false) }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .consumeWindowInsets(paddingValues)
                .padding(horizontal = 40.dp)
                .fillMaxHeight(),
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
                modifier = Modifier.fillMaxWidth(),
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
                    var validationSuccess = true

                    if (email.isEmpty()) {
                        emailError = true
                        validationSuccess = false
                    }
                    if (password.isEmpty()) {
                        passwordError = true
                        validationSuccess = false
                    }

                    // Call login
                    if (validationSuccess) {
                        onContinue.invoke(email, password)
                    }
                },
            ) {
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = stringResource(id = R.string.continue_label),
                )
            }

            Row(
                modifier = Modifier.padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = rememberCredentials,
                    onCheckedChange = onRememberCredentialsChange
                )
                Text(text = stringResource(id = R.string.remember_me))
            }
        }
    }

    if (showErrorDialog) {
        AlertDialog(onDismissRequest = onDismissErrorDialog,
            title = { Text(text = stringResource(id = R.string.login_failed)) },
            text = { Text(text = stringResource(id = R.string.login_failed_description)) },
            confirmButton = {
                Button(onClick = onDismissErrorDialog) {
                    Text(text = stringResource(id = R.string.ok_label))
                }
            })
    } else if (showWaitDialog) {
        Dialog(
            onDismissRequest = {},
            content = {
                Card {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            16.dp,
                            alignment = Alignment.CenterVertically
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.wait),
                            textAlign = TextAlign.Center,
                        )
                        CircularProgressIndicator()
                    }
                }
            },
        )
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
fun LoginViewLightPreview() {
    TimetonicApplicationTheme {
        var rememberCredentials by remember {
            mutableStateOf(false)
        }

        LoginView(rememberCredentials = rememberCredentials, onRememberCredentialsChange = {
            rememberCredentials = !rememberCredentials
        }) { _, _ -> }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun LoginViewDarkPreview() {
    TimetonicApplicationTheme {
        var rememberCredentials by remember {
            mutableStateOf(true)
        }

        LoginView(rememberCredentials = rememberCredentials, onRememberCredentialsChange = {
            rememberCredentials = !rememberCredentials
        }) { _, _ -> }
    }
}

@Composable
@Preview
fun LoginViewErrorPreview() {
    TimetonicApplicationTheme {
        LoginView(
            showErrorDialog = true,
            rememberCredentials = false,
            onRememberCredentialsChange = {},
        ) { _, _ -> }
    }
}

@Composable
@Preview
fun LoginViewWaitPreview() {
    TimetonicApplicationTheme {
        LoginView(
            showWaitDialog = true,
            rememberCredentials = false,
            onRememberCredentialsChange = {},
        ) { _, _ -> }
    }
}