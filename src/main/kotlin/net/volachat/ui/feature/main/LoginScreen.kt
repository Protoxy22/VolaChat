package net.volachat.ui.feature.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    viewModel: LoginViewModel,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource("drawables/logo.png"),
                    modifier = Modifier.size(100.dp),
                    contentDescription = "Logo"
                )
            }
            Text(
                text = "VolaChat",
                style = MaterialTheme.typography.h3
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            var username by remember { mutableStateOf("") }
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") }
            )
            Spacer(
                modifier = Modifier.height(10.dp)
            )
            var password by remember { mutableStateOf("") }
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .semantics { contentDescription = "*" },
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = {
                        viewModel.onLogin(username, password)
                    }
                ) {
                    Text(text = "Log In")
                }
                Spacer(
                    modifier = Modifier.width(40.dp)
                )
                Button(
                    onClick = {
                        viewModel.onRegister(username, password)
                        username = ""
                        password = ""
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF084C61))

                ) {
                    Text(text = "Register")
                }
            }
            Box(
                contentAlignment = Alignment.Center
            ) {
                if (viewModel.isBadLogin) {
                    Text(
                        "Bad login, please retry", textAlign = TextAlign.Center,
                        modifier = Modifier.width(150.dp), color = Color.Red
                    )
                }
            }
        }
    }
}